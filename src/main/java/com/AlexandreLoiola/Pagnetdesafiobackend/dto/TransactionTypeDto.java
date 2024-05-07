package com.AlexandreLoiola.Pagnetdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypeDto {
  private String description;
  private char flowDirection;
}
