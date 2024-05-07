package com.AlexandreLoiola.Pagnetdesafiobackend.service.exception;

public class InvalidDateFormatException extends IllegalArgumentException {
  public InvalidDateFormatException(String message) {
    super(message);
  }
}
