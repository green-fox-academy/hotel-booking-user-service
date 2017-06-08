package com.greenfox.model;

public class Hearthbeat {
String status;
String database;


  public Hearthbeat() {
    this.status = "ok";
    this.database = "ok";
  }

  public Hearthbeat(String status, String database) {
    this.status = "ok";
    this.database = "ok";
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
