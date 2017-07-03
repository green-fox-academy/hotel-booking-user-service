package com.greenfox.users;

import com.greenfox.register.model.Account;
import com.greenfox.register.model.Data;
import com.greenfox.register.model.RequestData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @GetMapping("/users")
  public Data returnUsers() {
    Account account = new Account(id, "john.doe@example.org", false, "validToken");
    Data data = new Data("user", account);
    RequestData requestData = new RequestData(data);
  }
}
