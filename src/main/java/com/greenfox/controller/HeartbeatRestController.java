package com.greenfox.controller;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import com.greenfox.service.HeartbeatService;
import com.greenfox.service.Consume;
import com.greenfox.service.Send;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatRestController {

  private final static Logger logger = LoggerFactory.getLogger("user-service.herokuapp.com");

  Send send = new Send();
  Consume consume = new Consume();

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
  public Heartbeat validateMessage() throws Exception {
    send.send("Send message");
    consume.consume();
    logger.debug("test debug message");
    logger.error("test error message");
    logger.info("GET /heartbeat endpoint called");
    logger.warn("test warn message");
    return heartbeatService.getHeartBeat(heartbeatRepository);
  }
}
