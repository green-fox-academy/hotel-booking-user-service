package com.greenfox.logging.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

  private final static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//                           Object handler) throws IOException {
//    if (response.getStatus() > 399){
//      logger.error(request.getServerName() + " / HTTP-ERROR " +  request.getRequestURI());
//    } else {
//      logger.info(request.getServerName() + " / HTTP-REQUEST " + request.getRequestURI());
//    }
//    return true;
//  }
}
