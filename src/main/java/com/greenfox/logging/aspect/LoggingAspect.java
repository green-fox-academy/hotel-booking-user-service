package com.greenfox.logging.aspect;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LoggingAspect {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @AfterReturning("restController() && args(.., request)")
  public void logInfoAdvice(HttpServletRequest request, JoinPoint joinPoint){
    logger.info(request.getServerName() + " / HTTP-REQUEST " + request.getRequestURI());
  }

  @AfterReturning("execution(@org.springframework.web.bind.annotation.*Mapping * *(..)) && args(.., request)")
  public void sayHi(HttpServletRequest request) throws Exception {
    System.out.println("hellobello" + request.getRequestURI());
  }

  @Pointcut("execution(@org.springframework.web.bind.annotation.*Mapping * *(..))")
  public void restController() {}

}