package com.greenfox.controller;

import com.greenfox.model.Account;
import com.greenfox.model.RequestData;
import com.greenfox.repository.AccountRepository;
import com.greenfox.service.UserCrudService;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCrudController {

  AccountRepository accountRepository;
  Page<Account> responsePage;
  UserCrudService userCrudService;

  @Autowired
  public UserCrudController(AccountRepository accountRepository, UserCrudService userCrudService) {
    this.accountRepository = accountRepository;
    this.userCrudService = userCrudService;
  }

  @GetMapping("/api/users")
  public ResponseEntity returnUsers(
      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(value = "admin", required = false) boolean admin,
      HttpServletRequest request) {
    responsePage = userCrudService.getUsersPage(request, admin, page);
    RequestData response = userCrudService.createResponse(userCrudService.buildLinks(request, page), "user", responsePage.getContent());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/api/users/{userId}")
  public ResponseEntity returnUser(@PathVariable(required = false) Long userId,
      HttpServletRequest request) {
    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      responsePage = accountRepository.findOneById(userId, new PageRequest(0, 1));
    } else {
      return new ResponseEntity<>(userCrudService.createErrorResponse(userId), HttpStatus.NOT_FOUND);
    }
    RequestData response = userCrudService.createResponse(userCrudService.buildLinks(request, 0), "user", responsePage.getContent());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping(value = "/api/users/{userId}", produces = "application/json")
  public ResponseEntity deleteUser(@PathVariable(required = false) Long userId,
      HttpServletRequest request) {
    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      accountRepository.delete(userId);
      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      return new ResponseEntity<>(userCrudService.createErrorResponse(userId), HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(value = "/api/users/{userId}")
  public ResponseEntity updateUser(@PathVariable(required = false) Long userId,
      HttpEntity request) throws Exception {
    HashMap map = (LinkedHashMap) request.getBody();
    HashMap map1 = (LinkedHashMap) map.get("data");
    HashMap map2 = (LinkedHashMap) map1.get("attributes");
    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      Account account = accountRepository.findOne(userId);
      if ((Boolean) map2.get("admin")) {
        account.setAdmin(true);
      }
      if (map2.get("email") != null) {
        account.setEmail((String) map2.get("email"));
      }
      accountRepository.save(account);
      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      return new ResponseEntity<>(userCrudService.createErrorResponse(userId), HttpStatus.NOT_FOUND);
    }
  }
}
