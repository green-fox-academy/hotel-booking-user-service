package com.greenfox.users;

import com.greenfox.guardian.model.Error;
import com.greenfox.guardian.model.ErrorResponse;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import com.greenfox.users.model.Links;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private AccountRepository accountRepository;
  private Page<Account> responsePage;

  @Autowired
  public Controller(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, @RequestParam(value = "admin", required = false) boolean admin,
      HttpServletRequest request) {

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

  @GetMapping("/api/users/{userId}")
  public ResponseEntity returnUser(@PathVariable(required = false) Long userId, HttpServletRequest request) {
    Links links = new Links();

    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      responsePage = accountRepository.findOneById(userId, new PageRequest(0, 1));
    } else {
      List<Error> tempList = new ArrayList<>();
      Error tempError = new Error("404",
          "Not Found",
          "No users found by id: " + userId);
      tempList.add(tempError);
      ErrorResponse tempResp = new ErrorResponse(tempList);
      return new ResponseEntity<>(tempResp, HttpStatus.NOT_FOUND);
    }

    links.setSelf(request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

    Data data = new Data(links, "user", responsePage.getContent());
    RequestData requestData = new RequestData(data);
    return new ResponseEntity<>(requestData, HttpStatus.OK);
  }

  @DeleteMapping (value = "/api/users/{userId}", produces = "application/json")
  public ResponseEntity deleteUser(@PathVariable(required = false) Long userId, HttpServletRequest request) {
      if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      accountRepository.delete(userId);
      return new ResponseEntity<>("{}",HttpStatus.OK);
    } else {
      List<Error> tempList = new ArrayList<>();
      Error tempError = new Error("404",
          "Not Found",
          "No users found by id: " + userId);
      tempList.add(tempError);
      ErrorResponse tempResp = new ErrorResponse(tempList);
      return new ResponseEntity<>(tempResp, HttpStatus.NOT_FOUND);
    }
  }

}
