package com.greenfox.controller;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import com.greenfox.service.HeartbeatService;
import com.greenfox.service.Consume;
import com.greenfox.service.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatRestController {

  Send send = new Send();
  Consume consume = new Consume();
  HeartbeatService heartbeatService;
  HeartbeatRepository heartbeatRepository;

  @Autowired
  public HeartbeatRestController(HeartbeatService heartbeatService, HeartbeatRepository heartbeatRepository) {
    this.heartbeatService = heartbeatService;
    this.heartbeatRepository = heartbeatRepository;
  }

  @GetMapping("/heartbeat")
  public Heartbeat validateMessage() throws Exception {
    return heartbeatService.getHeartBeat(heartbeatRepository);
  }

  @GetMapping("/sendevent")
  public void sendEvent() throws Exception {
    send.dispatch("user-service.herokuapp.com", "hello");
  }
}
