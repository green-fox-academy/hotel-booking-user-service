package com.greenfox.users;

import com.greenfox.guardian.model.Error;
import com.greenfox.guardian.model.ErrorResponse;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import com.greenfox.register.repository.AccountRepository;
import com.greenfox.users.model.Links;
import com.greenfox.users.service.UserCrudService;
import java.util.ArrayList;
import java.util.List;
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
      HttpServletRequest request, @RequestBody RequestData requestData) {
    if (!(accountRepository.findOneById(userId, new PageRequest(0, 1)).getContent().size() == 0)) {
      Account accountToUpdate = accountRepository.findOne(userId);
      accountToUpdate.setId(userId + 1);
      accountToUpdate.setAdmin(false);
      accountRepository.save(accountToUpdate);
      return new ResponseEntity<>("{}", HttpStatus.OK);
    } else {
      return new ResponseEntity<>(userCrudService.createErrorResponse(userId), HttpStatus.NOT_FOUND);
    }
  }
}
