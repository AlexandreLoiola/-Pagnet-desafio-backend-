package com.AlexandreLoiola.Pagnetdesafiobackend.repository;

import com.AlexandreLoiola.Pagnetdesafiobackend.model.TransactionModel;
import com.AlexandreLoiola.Pagnetdesafiobackend.model.TransactionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeModel, UUID> {

}
