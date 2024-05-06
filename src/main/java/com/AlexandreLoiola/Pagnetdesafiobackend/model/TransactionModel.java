package com.AlexandreLoiola.Pagnetdesafiobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "TB_TRANSACTION")
@Data
public class TransactionModel {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "date", length = 8, nullable = false)
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "transaction_type_id")
  private TransactionTypeModel transactionType;

  @Column(name = "transaction_hour", length = 6, nullable = false)
  private LocalTime hour;

  @Column(name = "amount", nullable = false)
  @DecimalMin(value = "0.00", inclusive = true)
  @DecimalMax(value = "9999999.99", inclusive = true)
  private float amount;

  @Column(name = "cpf", length = 11, nullable = false)
  private String cpf;

  @Column(name = "card", length = 12, nullable = false)
  private String card;

  @Column(name = "owner_store", length = 14, nullable = false)
  private String ownerStore;

  @Column(name = "name_store", length = 19, nullable = false)
  private String nameStore;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;
}
