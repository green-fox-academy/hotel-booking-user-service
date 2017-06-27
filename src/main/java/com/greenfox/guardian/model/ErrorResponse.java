package com.greenfox.guardian.model;

import java.util.List;

public class ErrorResponse {

  private List errors;

  public ErrorResponse(List errors) {
    this.errors = errors;
  }

  public List getErrors() {
    return errors;
  }
}
