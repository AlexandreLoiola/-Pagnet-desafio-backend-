package com.AlexandreLoiola.Pagnetdesafiobackend.service.exception;

public class InvalidAmountFormatException extends IllegalArgumentException {
  public InvalidAmountFormatException(String message) {
    super(message);
  }
}
