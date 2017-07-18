package com.greenfox.guardian.controller;

import com.greenfox.guardian.model.Error;
import com.greenfox.guardian.model.ErrorResponse;
import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GuardedEndController {

  @GetMapping("/user/{id}")
  public ResponseEntity getAuthorizedUser(
      @PathVariable("id") Long id,
      @RequestHeader(required = false, value = "Authorization") String authorization) {
    if (authorization == null || authorization.equals("Bearer " + "invalidToken")) {
      List<Error> errorList = new ArrayList<>();
      Error error = new Error("401",
          "Unauthorized",
          "No token is provided");
      errorList.add(error);
      ErrorResponse errorResp = new ErrorResponse(errorList);
      return new ResponseEntity<>(errorResp, HttpStatus.UNAUTHORIZED);
    } else {
      Account account = new Account(id, "john.doe@example.org", false, "validToken");
      Data data = new Data("user", account);
      RequestData requestData = new RequestData(data);
      return new ResponseEntity<>(requestData, HttpStatus.CREATED);
    }

  }
}
