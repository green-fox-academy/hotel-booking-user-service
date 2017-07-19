package com.greenfox.model;

public class Error {
  private String status;
  private String title;
  private String detail;

  public Error(String status, String title, String detail) {
    this.status = status;
    this.title = title;
    this.detail = detail;
  }

  public String getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public String getDetail() {
    return detail;
  }
}
