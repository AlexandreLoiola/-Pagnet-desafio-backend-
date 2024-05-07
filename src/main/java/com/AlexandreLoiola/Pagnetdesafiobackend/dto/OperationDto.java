package com.AlexandreLoiola.Pagnetdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
  private String description;
  private char direction;
  private double partialValue;
}
