package com.greenfox.register.controller;

import com.greenfox.register.model.Attributes;
import com.greenfox.register.service.JwtCreator;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

  AccountRepository accountRepository;
  JwtCreator jwtCreator;

  @Autowired
  public UserServiceController(AccountRepository accountRepository, JwtCreator jwtCreator) {
    this.jwtCreator = jwtCreator;
    this.accountRepository = accountRepository;
  }

  @PostMapping("/register")
  public ResponseEntity saveAccount(@RequestBody RequestData data, HttpServletRequest request) throws Exception {
    String jwt = jwtCreator.createJwt("hotel-booking-user-service","new user", 300000);

    Attributes attributes = (Attributes) data.getData().getAttributes();
    String email = attributes.getEmail();
    accountRepository.save(new Account(email, false, jwt));
    Account responseAccount = accountRepository.findAccountByEmail(email);
    Data responseData = new Data("user",responseAccount);
    RequestData response = new RequestData(responseData);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
