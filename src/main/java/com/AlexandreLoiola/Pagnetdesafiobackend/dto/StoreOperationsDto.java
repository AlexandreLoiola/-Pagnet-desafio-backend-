package com.AlexandreLoiola.Pagnetdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreOperationsDto {
  private String storeName;
  private Set<OperationDto> operations = new HashSet<>();
  private double totalBalance;

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.00");
    return "StoreOperationsDto{" +
      "storeName='" + storeName + '\'' +
      ", operations=" + operations +
      ", totalBalance=" + df.format(totalBalance) +
      '}';
  }
}
