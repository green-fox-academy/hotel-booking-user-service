package com.greenfox.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hearthbeat")
public class Status {

  private boolean status;

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

}
