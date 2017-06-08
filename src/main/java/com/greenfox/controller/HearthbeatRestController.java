package com.greenfox.controller;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HearthbeatRestController {

  @Autowired
  HearthbeatRepository hearthbeatRepository;

  @GetMapping("/hearthbeat")
  public Hearthbeat validateMessage() {
    Hearthbeat hearthbeat = new Hearthbeat();
    hearthbeatRepository.save(new Hearthbeat());
    if (hearthbeatRepository.count() == 0) {
      hearthbeat.setDatabase("error");
    }
    return hearthbeat;
  }
}
