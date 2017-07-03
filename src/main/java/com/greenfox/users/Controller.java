package com.greenfox.users;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private AccountRepository accountRepository;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/users")
  public ResponseEntity returnUsers() {
    Iterable<Account> users = accountRepository.findAll();
    Data data = new Data("user", users);
    data.add(linkTo(methodOn(Controller.class).returnUsers()).withRel("self"));
    data.add(linkTo(methodOn(Controller.class).returnUsers()).withRel("next"));
    data.add(linkTo(methodOn(Controller.class).returnUsers()).withRel("last"));
    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }
}
