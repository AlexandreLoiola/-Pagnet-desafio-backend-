package com.AlexandreLoiola.Pagnetdesafiobackend.repository;

import com.AlexandreLoiola.Pagnetdesafiobackend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
  Set<TransactionModel> findAllByIsActiveTrue();
}
