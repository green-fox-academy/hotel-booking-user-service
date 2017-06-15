package com.greenfox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

  private final static Logger logger = LoggerFactory.getLogger("com.greenfox.service.LoggerInterceptor");

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler) throws IOException {
    logger.info(request.getMethod() + " " + request.getRequestURI() + " endpoint called");
    return true;
  }
}
