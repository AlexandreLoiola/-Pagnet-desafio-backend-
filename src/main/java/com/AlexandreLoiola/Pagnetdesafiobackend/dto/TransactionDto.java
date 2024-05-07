package com.AlexandreLoiola.Pagnetdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
  private String nameStore;
  private String ownerStore;
  private String cpf;
  private float amount;
  private String card;
  private LocalDate date;
  private LocalTime hour;
  private TransactionTypeDto type;
}
