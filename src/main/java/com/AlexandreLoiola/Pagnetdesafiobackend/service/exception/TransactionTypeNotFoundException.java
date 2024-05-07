package com.AlexandreLoiola.Pagnetdesafiobackend.service.exception;

public class TransactionTypeNotFoundException extends RuntimeException {

  public TransactionTypeNotFoundException(String message) {
    super(message);
  }
}
