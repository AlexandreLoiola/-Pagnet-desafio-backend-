package com.AlexandreLoiola.Pagnetdesafiobackend.service;

import com.AlexandreLoiola.Pagnetdesafiobackend.dto.StoreOperationsDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.dto.TransactionDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.dto.OperationDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.dto.TransactionTypeDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.model.TransactionModel;
import com.AlexandreLoiola.Pagnetdesafiobackend.model.TransactionTypeModel;
import com.AlexandreLoiola.Pagnetdesafiobackend.repository.TransactionRepository;
import com.AlexandreLoiola.Pagnetdesafiobackend.repository.TransactionTypeRepository;
import com.AlexandreLoiola.Pagnetdesafiobackend.service.exception.FileProcessingException;
import com.AlexandreLoiola.Pagnetdesafiobackend.service.exception.InvalidAmountFormatException;
import com.AlexandreLoiola.Pagnetdesafiobackend.service.exception.InvalidDateFormatException;
import com.AlexandreLoiola.Pagnetdesafiobackend.service.exception.TransactionTypeNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TransactionService {

  private TransactionRepository transactionRepository;
  private TransactionTypeRepository transactionTypeRepository;

  public TransactionService(TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository) {
    this.transactionRepository = transactionRepository;
      this.transactionTypeRepository = transactionTypeRepository;
  }

  public Set<TransactionDto> processUploadedTransactions(MultipartFile file) {
    Set<TransactionDto> transactionDtos = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        TransactionModel transactionModel =  parseTxtFileToModel(line);
        TransactionDto transactionDto = registerTransactions(transactionModel);
        transactionDtos.add(transactionDto);
      }
    } catch (Exception e) {
      throw new FileProcessingException("Error processing file");
    }
    return transactionDtos;
  }

  public Set<StoreOperationsDto> getStoreOperations() {
    Map<String, StoreOperationsDto> storeOperationsMap = new HashMap<>();
    Set<TransactionModel> transactionModels = transactionRepository.findAllByIsActiveTrue();

    for (TransactionModel transactionModel : transactionModels) {
      String storeName = transactionModel.getNameStore();
      StoreOperationsDto storeOperationsDto = storeOperationsMap.getOrDefault(storeName, new StoreOperationsDto());
      storeOperationsDto.setStoreName(storeName);

      TransactionTypeModel transactionTypeModel = transactionTypeRepository.findByTypeAndIsActiveTrue(transactionModel.getTransactionType().getType()).orElseThrow(() -> new RuntimeException("Transaction type Not Found"));
      Set<OperationDto> operationDtos = storeOperationsDto.getOperations();

      var operationDto = new OperationDto();
      operationDto.setDescription(transactionTypeModel.getDescription());
      operationDto.setDirection(transactionTypeModel.getFlowDirection());
      operationDto.setPartialValue(transactionModel.getAmount());

      operationDtos.add(operationDto);
      storeOperationsDto.setOperations(operationDtos);
      storeOperationsDto.setTotalBalance(storeOperationsDto.getTotalBalance() + transactionModel.getAmount());

      storeOperationsMap.put(storeName, storeOperationsDto);
    }
    return new HashSet<>(storeOperationsMap.values());
  }

  private TransactionTypeModel findTransactionTypeModel(char type) {
    return transactionTypeRepository.findByTypeAndIsActiveTrue(type).orElseThrow(() -> new RuntimeException("Cannot find the transaction type"));
  }

  @Transactional
  public TransactionDto registerTransactions(TransactionModel transactionModel) {
    transactionModel.setActive(true);
    transactionRepository.save(transactionModel);
    return convertModelToDto(transactionModel);
  }

  TransactionModel parseTxtFileToModel(String line) {
    TransactionModel transactionModel = new TransactionModel();
    TransactionTypeModel transactionTypeModel = findTransactionTypeModel(line.charAt(0));
    try {
      transactionModel.setTransactionType(transactionTypeModel);
    } catch (Exception err) {
      throw new TransactionTypeNotFoundException("Cannot find the transaction type");
    }
    try {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      transactionModel.setDate(LocalDate.parse(line.substring(1, 9), dateFormatter));
      DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HHmmss");
      transactionModel.setHour(LocalTime.parse(line.substring(42, 48), hourFormatter));
    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException("Invalid date format");
    }
    try {
      float amount = Float.parseFloat(line.substring(10, 19)) / 100;
      if (transactionTypeModel.getFlowDirection() == '-') {
        amount *= -1;
      }
      transactionModel.setAmount(amount);
    } catch (NumberFormatException e) {
      throw new InvalidAmountFormatException("Invalid amount format");
    }
    transactionModel.setCpf(line.substring(20, 30));
    transactionModel.setCard(line.substring(31, 42));
    transactionModel.setOwnerStore(line.substring(48, 62));
    transactionModel.setNameStore(line.substring(62));
    return transactionModel;
  }

  TransactionDto convertModelToDto(TransactionModel transactionModel) {
    if (transactionModel == null) {
      throw new IllegalArgumentException("TransactionModel cannot be null");
    }
    var transactionDto = new TransactionDto();
    try {
      TransactionTypeModel transactionTypeModel = findTransactionTypeModel(transactionModel.getTransactionType().getType());
      var transactionTypeDto = new TransactionTypeDto();
      transactionTypeDto.setDescription(transactionTypeModel.getDescription());
      transactionTypeDto.setFlowDirection(transactionTypeModel.getFlowDirection());
      transactionDto.setType(transactionTypeDto);
    } catch (Exception e) {
      throw new TransactionTypeNotFoundException("Not Found Transaction Type");
    }
    try {
      transactionDto.setDate(transactionModel.getDate());
      transactionDto.setHour(transactionModel.getHour());
    } catch (DateTimeException e) {
      throw new InvalidDateFormatException("Invalid date or time in TransactionModel");
    }
    try {
      transactionDto.setAmount(transactionModel.getAmount());
    } catch (NumberFormatException e) {
      throw new InvalidAmountFormatException("Invalid amount in TransactionModel");
    }
    transactionDto.setCpf(transactionModel.getCpf());
    transactionDto.setCard(transactionModel.getCard());
    transactionDto.setOwnerStore(transactionModel.getOwnerStore());
    transactionDto.setNameStore(transactionModel.getNameStore());
    return transactionDto;
  }
}
