package com.greenfox.users;

import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import com.greenfox.users.model.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

  private AccountRepository accountRepository;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(@RequestParam(required = false) Integer page, HttpServletRequest request) {
    if (page == null) {
      page = 0;
    }
    Page<Account> usersPage = accountRepository.findAll(new PageRequest(page,2));
    Links links = new Links();

    if (request.getParameter("page") != null) {
      links.setSelf(request.getRequestURL().toString() + "?page=" + (page));
    } else {
      links.setSelf(request.getRequestURL().toString());
    }


    if(usersPage.hasNext()) {
      links.setNext(request.getRequestURL() + "?page=" + (page + 1));
      links.setLast(request.getRequestURL() + "?page=" + usersPage.getTotalPages());
    }
    if (usersPage.hasPrevious()) {
      links.setPrev(request.getRequestURL() + "?page=" + (page - 1));
    }

    System.out.println(usersPage.toString());
    Data data = new Data(links,"user", usersPage);
    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }
}
