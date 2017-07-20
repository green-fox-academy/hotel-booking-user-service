package com.greenfox.service;

import com.greenfox.model.Account;
import com.greenfox.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonApi {

  @Autowired
  GsonService gsonService;
  @Autowired
  AccountRepository accountRepository;

  public JsonApi(GsonService gsonService, AccountRepository accountRepository) {
    this.gsonService = gsonService;
    this.accountRepository = accountRepository;
  }

  public String createResponse(String email) {
    Account account = accountRepository.findAccountByEmail(email);
    return gsonService.createAccountJson(account.getId(), account.getEmail(),
        account.isAdmin(), account.getToken());
  }
}
