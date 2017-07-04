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
import java.util.Enumeration;

@RestController
public class Controller {

  private AccountRepository accountRepository;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "admin", required = false) boolean admin,
      HttpServletRequest request) {


    if (isAdminQuery(request)) {
      adminFilterService();
    }


    if (page == null) {
      page = 0;
    }

    Object requestedPage = new Object();
    Page<Account> usersPage = accountRepository.findAll(new PageRequest(page, 2));
    Links links = new Links();

    links.setSelf(
        request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request
            .getQueryString() : ""));
    if (usersPage.hasNext()) {
      links.setNext(request.getRequestURL() + "?page=" + (page + 1));
      links.setLast(request.getRequestURL() + "?page=" + usersPage.getTotalPages());
    }
    if (usersPage.hasPrevious()) {
      links.setPrev(request.getRequestURL() + (request.getQueryString().endsWith("page=1") ? "" : "?page=" + (page - 1)));
    }

    Page<Account> usersByRole = accountRepository.findAllByAdmin(admin, new PageRequest(page, 2));

    System.out.println(usersPage.toString());

    Data data = new Data(links, "user", usersByRole.getContent());
    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }

  public boolean isAdminQuery(HttpServletRequest request) {
    Enumeration parameterNames = request.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String parameterName = parameterNames.nextElement().toString();
      if (parameterName.equals("admin")) {
        return true;
      }
    }
    return false;
  }
}
