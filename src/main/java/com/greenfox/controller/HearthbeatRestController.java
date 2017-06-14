package com.greenfox.controller;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import com.greenfox.service.HearthbeatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HearthbeatRestController {
  private final static Logger LOGGER = LoggerFactory.getLogger(HearthbeatRestController.class.getName());
  private HearthbeatService hearthbeatService;
  private HearthbeatRepository hearthbeatRepository;

  @Autowired
  public HearthbeatRestController(HearthbeatService hearthbeatService, HearthbeatRepository hearthbeatRepository) {
    this.hearthbeatService = hearthbeatService;
    this.hearthbeatRepository = hearthbeatRepository;
  }

  @GetMapping("/hearthbeat")
  public Hearthbeat validateMessage() {
    LOGGER.trace("trace message");
    LOGGER.debug("debug message");
    LOGGER.info("info message");
    LOGGER.warn("warn message");
    LOGGER.error("error message");
    return hearthbeatService.getHearthBeat(hearthbeatRepository);
  }
}
