package com.greenfox.controller;

import com.greenfox.model.Account;
import com.greenfox.model.Data;
import com.greenfox.model.RequestData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

  @PostMapping("/register")
  public Account saveAccount(@RequestBody RequestData data) {
    String email = data.getData().getAttributes().getEmail();
    String password = data.getData().getAttributes().getPassword();
    return (new Account(email,password));
  }
}
