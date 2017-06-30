package com.greenfox.register.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  @JsonIgnore
  private String password;

  public Account() {
  }

  public Account(String email, boolean admin, String token, String password) {
    this.email = email;
    this.admin = admin;
    this.token = token;
    this.password = password;
  }

  public Account(String email, boolean admin, String password) {
    this.email = email;
    this.admin = admin;
    this.password = password;
  }

  public Account(Long id, String email, boolean admin, String token) {
    this.id = id;
    this.email = email;
    this.admin = admin;
    this.token = token;
  }

  public Account(Long id, String email, boolean admin, String token, String password) {
    this.id = id;
    this.email = email;
    this.admin = admin;
    this.token = token;
    this.password = password;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
