package com.greenfox.service;

import com.google.gson.Gson;
import com.greenfox.exception.InvalidPasswordException;
import com.greenfox.exception.NoSuchAccountException;
import com.greenfox.model.Account;
import com.greenfox.repository.AccountRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  GsonService gsonService;
  @Autowired
  AccountRepository accountRepository;


  public AuthService(GsonService gsonService, AccountRepository accountRepository) {
    this.gsonService = gsonService;
    this.accountRepository = accountRepository;
  }

  public Account getCredentials(String json) {
    return gsonService.parseCredentials(json);
  }

//  public String createJwt(Account a) throws Exception {
//    return jwtUnit.generateToken(a);
//  }

  public String hashPassword(String password) {
    return BCrypt
        .hashpw(password, BCrypt.gensalt((Integer.parseInt(System.getenv("LOG_ROUNDS")))));
  }

  public Account authenticateUser(String email, String password) throws Exception {
    return authenticate(email, password);
  }

  public Account authenticate(String email, String password) throws Exception {
    if (!isRegisteredUser(email)) {
      throw new NoSuchAccountException("Invalid email");
    }

    Account account = accountRepository.findAccountByEmail(email);

    if (!checkPassword(password, account.getPassword())) {
      throw new InvalidPasswordException("Invalid password");
    }
    return account;

  }

  public boolean isRegisteredUser(String email) {
    return (accountRepository.findAccountByEmail(email) != null);
  }

  public boolean checkPassword(String password, String pw_hashed) {
    return BCrypt.checkpw(password, pw_hashed);
  }

}
