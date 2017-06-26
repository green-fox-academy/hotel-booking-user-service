package com.greenfox.controller;

import com.greenfox.model.Attributes;
import io.jsonwebtoken.*;
import com.greenfox.model.Account;
import com.greenfox.model.Data;
import com.greenfox.model.RequestData;
import com.greenfox.repository.AccountRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

  AccountRepository accountRepository;

  @Autowired
  public UserServiceController(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @PostMapping("/register")
  public ResponseEntity saveAccount(@RequestBody RequestData data) throws Exception {

    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + 300000;
    Date expiration = new Date(expMillis);

    Attributes attributes = (Attributes) data.getData().getAttributes();

    String jwt = Jwts.builder()
        .setSubject("new user")
        .setExpiration(expiration)
        .claim("name", "John Doe")
        .signWith(
            SignatureAlgorithm.HS256,
            attributes.getPassword().getBytes("UTF-8")
        )
        .compact();

    String email = attributes.getEmail();
    accountRepository.save(new Account(email, false, jwt));

    Account responseAccount = accountRepository.findAccountByEmail(email);
    System.out.println(responseAccount.getId());
    Data responseData = new Data("user",responseAccount);
    RequestData response = new RequestData(responseData);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
