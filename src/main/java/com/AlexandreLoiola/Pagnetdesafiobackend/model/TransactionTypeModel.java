package com.AlexandreLoiola.Pagnetdesafiobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TB_TRANSACTION_TYPE")
@Data
public class TransactionTypeModel {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "description", length = 40, nullable = false, unique = true)
  private String description;

  @Column(name = "flow_direction", length = 1, nullable = false)
  private char flow_direction;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @OneToOne(mappedBy = "transactionType")
  private TransactionModel transaction;
}
