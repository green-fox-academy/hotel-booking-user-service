package com.greenfox.controller;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import com.greenfox.service.HeartbeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatRestController {
  private final static Logger LOGGER = LoggerFactory.getLogger(HeartbeatRestController.class.getName());
  private HeartbeatService heartbeatService;
  private HeartbeatRepository heartbeatRepository;

  @Autowired
  public HeartbeatRestController(HeartbeatService heartbeatService, HeartbeatRepository heartbeatRepository) {
    this.heartbeatService = heartbeatService;
    this.heartbeatRepository = heartbeatRepository;
  }

  @GetMapping("/heartbeat")
  public Heartbeat validateMessage() {
    LOGGER.trace("trace message");
    LOGGER.debug("debug message");
    LOGGER.info("info message");
    LOGGER.warn("warn message");
    LOGGER.error("error message");
    return heartbeatService.getHeartBeat(heartbeatRepository);
  }
}
