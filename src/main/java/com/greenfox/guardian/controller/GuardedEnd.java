package com.greenfox.guardian.controller;

import com.greenfox.guardian.model.Error;
import com.greenfox.guardian.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GuardedEnd {

  @GetMapping("/user/{id}")
  public ResponseEntity getUserRelatedStuff(@RequestHeader(required = false, value = "Authorization") String authorization) {
    if (authorization == null || authorization.equals("Bearer " + "invalidToken")) {
      List tempList = new ArrayList();
      Error tempError = new Error("401",
              "Unauthorized",
              "No token is provided");
      tempList.add(tempError);
      ErrorResponse tempResp = new ErrorResponse(tempList);
      return new ResponseEntity(tempResp, HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity(new String("{ here : 'u go' }"), HttpStatus.CREATED);
    }

  }
}
