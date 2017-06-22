package com.greenfox.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private boolean admin;
  private String token;

  public Account() {
  }

  public Account(String email, boolean admin, String token) {
    this.email = email;
    this.admin = admin;
    this.token = token;
  }

  public Account(String email, boolean admin) {
    this.email = email;
    this.admin = admin;
  }

  public Account(Long id, String email, boolean admin, String token) {
    this.id = id;
    this.email = email;
    this.admin = admin;
    this.token = token;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
