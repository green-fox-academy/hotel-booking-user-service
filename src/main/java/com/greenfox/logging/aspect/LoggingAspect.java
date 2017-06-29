package com.greenfox.logging.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Service
public class LoggingAspect {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Pointcut("execution(@org.springframework.web.bind.annotation.*Mapping * *(..))")
  public void restController() {
  }

  @AfterReturning(pointcut = "restController() && args(.., request)")
  public void logInfoAdvice(HttpServletRequest request) {
      logger.info(request.getServerName() + " / HTTP-REQUEST " + request.getRequestURI());
    }

  @AfterReturning(pointcut = "restController() && args(.., request)")
  public void logErrorAdvice(HttpServletRequest request) {
    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getResponse();
    if (response.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
      logger.error(request.getServerName() + " / HTTP-ERROR " + request.getRequestURI());
    }
  }

  @After("restController()")
  public void logAfterThrowing(JoinPoint joinPoint) {
    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getResponse();
    if (response.getStatus() > 399) {
      logger.error("Error status : " + response.getStatus());
    }
  }

}