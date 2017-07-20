package com.greenfox.controller;

import com.greenfox.exception.InvalidPasswordException;
import com.greenfox.exception.NoSuchAccountException;
import com.greenfox.model.Account;
import com.greenfox.repository.AccountRepository;
import com.greenfox.service.AuthService;
import com.greenfox.service.JsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

  @Autowired
  AuthService authService;
  @Autowired
  JsonApi jsonApi;
  @Autowired
  AccountRepository accountRepository;

  public AuthController(AuthService authService, JsonApi jsonApi, AccountRepository accountRepository) {
    this.authService = authService;
    this.jsonApi = jsonApi;
    this.accountRepository = accountRepository;
  }

  @PostMapping(value = "/register", produces = "application/json")
  public ResponseEntity saveAccount(@RequestBody String json) throws Exception {
    Account account = authService.getCredentials(json);
    if (authService.isRegisteredUser(account.getEmail())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    } else {
      Account newAccount = new Account();
      newAccount.setPassword(authService.hashPassword(account.getPassword()));
      newAccount.setEmail(account.getEmail());
      accountRepository.save(newAccount);
      newAccount = accountRepository.findAccountByEmail(account.getEmail());
//      newAccount.setToken(authService.createJwt(newAccount));
      accountRepository.save(newAccount);
      String response = jsonApi.createResponse(newAccount.getEmail());
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
  }

  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity authenticateAccount(@RequestBody String json) throws Exception {

    Account account = authService.getCredentials(json);

    try {
      Account loginAccount = authService.authenticateUser(account.getEmail(), account.getPassword());
//      account.setToken(createJwt(account));
//      accountRepository.save(loginaccount);
      String response = jsonApi.createResponse(account.getEmail());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NoSuchAccountException | InvalidPasswordException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
