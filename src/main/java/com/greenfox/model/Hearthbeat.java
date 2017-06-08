package com.greenfox.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hearthbeat")
@Entity
public class Hearthbeat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;
  String status;
  @Column(name = "dbase")
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
