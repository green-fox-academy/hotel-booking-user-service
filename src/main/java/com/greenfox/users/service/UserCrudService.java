package com.greenfox.users.service;

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
    return isInQuery(request, parameter) ? request.getParameter(parameter) : null;
  }

  public RequestData createResponse(Links links, String type, Object attributes) {
    Data data = new Data(links, type, attributes);
    return new RequestData(data);
  }

  public Links buildLinks(HttpServletRequest request, int page) {
    Links links = new Links();
    links.setSelf(
        request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request
            .getQueryString() : ""));
    if (responsePage.hasNext()) {
      links.setNext(request.getRequestURL() + "?page=" + (page + 1));
      links.setLast(request.getRequestURL() + "?page=" + responsePage.getTotalPages());
    }
    if (responsePage.hasPrevious()) {
      links.setPrev(request.getRequestURL() + (request.getQueryString().endsWith("page=1") ? ""
          : "?page=" + (page - 1)));
    }
    return links;
  }

  public Page getUsersPage(HttpServletRequest request, boolean admin, int page) {
    if (isInQuery(request, "admin")) {
      responsePage = getUsers(admin, page);
    } else {
      responsePage = accountRepository.findAll(new PageRequest(page, 20));
    }
    return responsePage;
  }

  public ErrorResponse createErrorResponse(Long userId) {
    List<Error> errors = new ArrayList<>();
    Error error = new Error("404",
        "Not Found",
        "No users found by id: " + userId);
    errors.add(error);
    return new ErrorResponse(errors);
  }
}
