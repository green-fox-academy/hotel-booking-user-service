package com.greenfox.logging.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
  @AfterReturning("execution(* com.greenfox.heartbeat.controller..*(..))")
  public void sayHi(){
    System.out.println("hellobello");
  }
}
