package com.greenfox.register.exception;

public class NoSuchAccountException extends Exception {

  public NoSuchAccountException() {
  }

  public NoSuchAccountException(String message) {
    super(message);
  }
}
