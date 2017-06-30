package com.greenfox.register.controller;

import com.greenfox.register.exception.InvalidPasswordException;
import com.greenfox.register.exception.NoSuchAccountException;
import com.greenfox.register.model.Attributes;
import com.greenfox.register.model.Credentials;
import com.greenfox.register.service.AuthService;
import com.greenfox.register.service.GsonService;
import com.greenfox.register.service.JwtCreator;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Attributes;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import com.greenfox.register.service.JwtCreator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserServiceController {

  AccountRepository accountRepository;
  JwtCreator jwtCreator;
  GsonService gsonService;
  AuthService authService;

  @Autowired
  public UserServiceController(AccountRepository accountRepository, JwtCreator jwtCreator, GsonService gsonService, AuthService authService) {
    this.jwtCreator = jwtCreator;
    this.accountRepository = accountRepository;
    this.gsonService = gsonService;
    this.authService = authService;
  }

  @PostMapping(value = "/register", produces = "application/json")
  public ResponseEntity saveAccount(@RequestBody String json) throws Exception {

    String jwt = jwtCreator.createJwt("hotel-booking-user-service","new user", 300000);
    Credentials credentials = gsonService.parseCredentials(json);

    if (!authService.checkAccount(credentials.getEmail())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    } else {
      String pw_hashed = BCrypt
          .hashpw(credentials.getPassword(), BCrypt.gensalt((Integer.parseInt(System.getenv("LOG_ROUNDS")))));
      accountRepository.save(new Account(credentials.getEmail(), false, jwt, pw_hashed));
      Account responseAccount = accountRepository.findAccountByEmail(credentials.getEmail());
      String response = gsonService.createAccountJson(responseAccount.getId(),responseAccount.getEmail(),responseAccount.isAdmin(), responseAccount.getToken());

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
  }

  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity authenticateAccount(@RequestBody String json) throws Exception {
    Credentials credentials = gsonService.parseCredentials(json);
    try {
      authService.authenticate(credentials.getEmail(),credentials.getPassword());
      Account responseAccount = accountRepository.findAccountByEmail(credentials.getEmail());
      String response = gsonService.createAccountJson(responseAccount.getId(),responseAccount.getEmail(),responseAccount.isAdmin(), responseAccount.getToken());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NoSuchAccountException|InvalidPasswordException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

}
