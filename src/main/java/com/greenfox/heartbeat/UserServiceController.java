package com.greenfox.heartbeat;

import com.greenfox.model.Attributes;
import com.greenfox.service.JwtCreator;
import com.greenfox.model.Account;
import com.greenfox.model.Data;
import com.greenfox.model.RequestData;
import com.greenfox.repository.AccountRepository;
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
  public ResponseEntity saveAccount(@RequestBody RequestData data) throws Exception {
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
