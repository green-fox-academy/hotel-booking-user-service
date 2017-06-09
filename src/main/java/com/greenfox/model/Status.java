package com.greenfox.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hearthbeat")
public class Status {

  private Boolean status;

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

}
