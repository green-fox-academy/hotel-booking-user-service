package com.greenfox.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

  private final static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, HttpServerErrorException error) throws IOException {
    if (response.getStatus() > 399){
      logger.error(request.getMethod() + " " + request.getRequestURI() + " endpoint called");
    } else {
      logger.info(request.getMethod() + " " + request.getRequestURI() + " endpoint called");
    }
    return true;
  }
}
