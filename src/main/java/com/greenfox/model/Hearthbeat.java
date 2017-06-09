package com.greenfox.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.stereotype.Component;

@Component
public class Hearthbeat {

  String status;
  @JsonInclude(Include.NON_NULL)
  String database;

  public Hearthbeat() {
  }

  public Hearthbeat(String status) {
    this.status = status;
  }

  public Hearthbeat(String status, String database) {
    this.status = status;
    this.database = database;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }
}
