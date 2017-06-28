package com.greenfox.register.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.greenfox.register.exception.InvalidPasswordException;
import com.greenfox.register.exception.NoSuchAccountException;
import com.greenfox.register.model.Attributes;
import com.greenfox.register.service.JwtCreator;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
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
    String password = attributes.getPassword();

    if (checkAccount(email)) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    } else {
      String pw_hashed = BCrypt
          .hashpw(password, BCrypt.gensalt((Integer.parseInt(System.getenv("LOG_ROUNDS")))));
      accountRepository.save(new Account(email, false, jwt, pw_hashed));
      RequestData response = buildJson(email);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
  }

  @PostMapping("/login")
  public ResponseEntity authenticateAccount(@RequestBody RequestData data) throws Exception {
    // get object from json
    Attributes attributes = (Attributes) data.getData().getAttributes();
    String email = attributes.getEmail();
    String password = attributes.getPassword();

    try {
      authenticate(email,password);
      RequestData response = buildJson(email);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NoSuchAccountException|InvalidPasswordException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  public String parseJson(String json) {
    JsonElement jelement = new JsonParser().parse(json);
    JsonObject data = jelement.getAsJsonObject();
    data = data.getAsJsonObject("data");
    JsonPrimitive type = data.getAsJsonPrimitive("type");
    System.out.println(type);
    JsonObject attributes = data.getAsJsonObject("attributes");
    String result = attributes.get("email").toString();
    System.out.println(result);
    return result;
  }

  @GetMapping("/createJson")
  public String createJson() {
    JsonObject jobject = new JsonObject();
    JsonObject data = new JsonObject();
    JsonObject attributes = new JsonObject();
    jobject.add("data", data);
    data.addProperty("type","user");
    data.add("attributes", attributes);
    attributes.addProperty("email", "dombo.peter@example.com");
    attributes.addProperty("password","suchsecret");
    System.out.println(jobject.toString());

    return jobject.toString();
  }

  private void authenticate(String email, String password) throws Exception {
    Account account;
    if (checkAccount(email)) {
      throw new NoSuchAccountException("Invalid email");
    } else {
      account = accountRepository.findAccountByEmail(email);
    }
    if (!checkPassword(password, account.getPassword())) {
      throw new InvalidPasswordException("Invalid password");
    };
  }

  private RequestData buildJson(String email) {
    Account responseAccount = accountRepository.findAccountByEmail(email);
    Data responseData = new Data("user",responseAccount);
    return new RequestData(responseData);
  }

  private boolean checkAccount(String email) {
    return (accountRepository.findAccountByEmail(email) == null);
  }

  private boolean checkPassword(String password, String pw_hashed) {
    return BCrypt.checkpw(password, pw_hashed);
  }
}
