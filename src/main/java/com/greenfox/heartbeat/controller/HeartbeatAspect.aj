package com.greenfox.heartbeat.controller;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public aspect HeartbeatAspect {

  @After(value = "execution(public * validateMessage())")
  public void heartbeatAdvice(){
    System.out.println("hellobello");
  }


}
