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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PatchMapping(value = "/api/users/{userId}")
  public ResponseEntity updateUser(@PathVariable(required = false) Long userId,
      HttpServletRequest request, @RequestBody RequestData requestData) throws Exception {
    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      Account account = accountRepository.findOne(userId);
      if (((Account) requestData.getData().getAttributes()).isAdmin()) {
        account.setAdmin(true);
      }
      if (((Account) requestData.getData().getAttributes()).getToken() != null) {
        account.setToken(((Account) requestData.getData().getAttributes()).getToken());
      }
      if (((Account) requestData.getData().getAttributes()).getEmail() != null) {
        account.setEmail(((Account) requestData.getData().getAttributes()).getEmail());
      }
//      if (((Account) requestData.getData().getAttributes()).getId() != null) {
//        account.setId(((Account) requestData.getData().getAttributes()).getId());
//      } //WHY CHANGE, NO WAY
//      if (((Account) requestData.getData().getAttributes()).getPassword() != null) {
//        account.setPassword(((Account) requestData.getData().getAttributes()).getPassword());
//      } //ALWAYS NULL
      accountRepository.save(account);

      Map<String, Object> map = introspect((requestData.getData().getAttributes()));
      List<Object> list = new ArrayList<>(map.keySet());
      List<Object> result = new ArrayList<>();
      list.stream()
          .filter(key -> map.get(key) != null)
          .forEach(result::add);

      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      return new ResponseEntity<>(userCrudService.createErrorResponse(userId), HttpStatus.NOT_FOUND);
    }
  }

  public static Map<String, Object> introspect(Object obj) throws Exception {
    Map<String, Object> result = new HashMap<>();
    BeanInfo info = Introspector.getBeanInfo(obj.getClass());
    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
      Method reader = pd.getReadMethod();
      if (reader != null)
        result.put(pd.getName(), reader.invoke(obj));
    }
    return result;
  }

}
