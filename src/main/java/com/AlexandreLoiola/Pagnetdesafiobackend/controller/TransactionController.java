package com.AlexandreLoiola.Pagnetdesafiobackend.controller;

import com.AlexandreLoiola.Pagnetdesafiobackend.dto.StoreOperationsDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.dto.TransactionDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.dto.OperationDto;
import com.AlexandreLoiola.Pagnetdesafiobackend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

  @PostMapping("/upload")
  public ResponseEntity<Set<TransactionDto>> uploadTransactionFileToRegistration(@RequestParam("file") MultipartFile file) {
    Set<TransactionDto> transactionDtos = transactionService.processUploadedTransactions(file);
    return ResponseEntity.ok().body(transactionDtos);
  }

  @GetMapping("/operation")
  public ResponseEntity<Set<StoreOperationsDto>> getOperationsByStore() {
    Set<StoreOperationsDto> storeOperationsDto = transactionService.getStoreOperations();
    return ResponseEntity.ok().body(storeOperationsDto);
  }
}
