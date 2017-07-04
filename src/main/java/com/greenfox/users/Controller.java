package com.greenfox.users;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private AccountRepository accountRepository;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(@RequestParam(required = false) Integer page) {
//    Page<Account> accounts = accountRepository.findAll(new PageRequest(0,10));
    Iterable<Account> users;
    Data data;

    if (page != null) {
      users = accountRepository.findAll(new PageRequest(page,20));
      data = new Data("user", users);
      data.add(new Link("https://hotel-booking-user-service.com/api/users","self"));
      data.add(linkTo(methodOn(Controller.class).returnUsers(page + 1)).withRel("next"));
      data.add(linkTo(methodOn(Controller.class).returnUsers(((Page) users).getTotalPages()))
          .withRel("last"));
    } else {
      users = accountRepository.findAll();
      data = new Data("user", users);
      data.add(new Link("https://hotel-booking-user-service.com/api/users","self"));
    }

    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }
}
