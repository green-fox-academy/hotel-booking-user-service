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
  private Page<Account> responsePage;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "admin", required = false) boolean admin,
      HttpServletRequest request) {

    if (page == null) {
      page = 0;
    }

    if (isAdminQuery(request)) {
      responsePage = adminFilterService(admin, page);
    } else {
      responsePage = accountRepository.findAll(new PageRequest(page, 2));
    }

    Links links = new Links();

    links.setSelf(
        request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request
            .getQueryString() : ""));
    if (responsePage.hasNext()) {
      links.setNext(request.getRequestURL() + "?page=" + (page + 1));
      links.setLast(request.getRequestURL() + "?page=" + responsePage.getTotalPages());
    }
    if (responsePage.hasPrevious()) {
      links.setPrev(request.getRequestURL() + (request.getQueryString().endsWith("page=1") ? "" : "?page=" + (page - 1)));
    }

    Data data = new Data(links, "user", responsePage.getContent());
    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }

  public Page<Account> adminFilterService(boolean admin, int page){
    return accountRepository.findAllByAdmin(admin, new PageRequest(page, 2));
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
