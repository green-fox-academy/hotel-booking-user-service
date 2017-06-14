package com.greenfox.controller;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import com.greenfox.service.HeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatRestController {

  HeartbeatService heartbeatService;
  HeartbeatRepository heartbeatRepository;

  @Autowired
  public HeartbeatRestController(HeartbeatService heartbeatService, HeartbeatRepository heartbeatRepository) {
    this.heartbeatService = heartbeatService;
    this.heartbeatRepository = heartbeatRepository;
  }

  @ExceptionHandler(Exception.class)
  public Heartbeat databaseError() {
    return new Heartbeat("ok");
  }

  @GetMapping("/heartbeat")
  public Heartbeat validateMessage() {
    return heartbeatService.getHearthBeat(heartbeatRepository);
  }
}
