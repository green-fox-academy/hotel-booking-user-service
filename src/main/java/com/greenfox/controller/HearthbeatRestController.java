package com.greenfox.controller;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import com.greenfox.service.HearthbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HearthbeatRestController {

  HearthbeatService hearthbeatService;
  HearthbeatRepository hearthbeatRepository;

  @Autowired
  public HearthbeatRestController(HearthbeatService hearthbeatService, HearthbeatRepository hearthbeatRepository) {
    this.hearthbeatService = hearthbeatService;
    this.hearthbeatRepository = hearthbeatRepository;
  }

  @GetMapping("/hearthbeat")
  public Hearthbeat validateMessage() {
    return hearthbeatService.getHearthBeat(hearthbeatRepository);
  }
}
