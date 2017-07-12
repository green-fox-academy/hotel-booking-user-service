package com.greenfox.users.service;

import com.greenfox.register.model.Account;
import com.greenfox.register.repository.AccountRepository;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserCrudService {

  @Autowired
  private AccountRepository accountRepository;
  private Page<Account> responsePage;

  public Page<Account> getUsers(boolean admin, int page) {
    return accountRepository.findAllByAdmin(admin, new PageRequest(page, 2));
  }

  public boolean isInQuery(HttpServletRequest request, String parameter) {
    Enumeration parameterNames = request.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String parameterName = parameterNames.nextElement().toString();
      if (parameterName.equals(parameter)) {
        return true;
      }
    }
    return false;
  }

  public List<String> getQuery(HttpServletRequest request) {
    List<String> params = new ArrayList<>();
    Enumeration parameterNames = request.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      params.add(parameterNames.nextElement().toString());
    }
    return params;
  }

  public String getQueryValue(HttpServletRequest request, String parameter) {
    if (isInQuery(request, parameter)) {
      return request.getParameter(parameter);
    } else {
      return null;
    }
  }

}
