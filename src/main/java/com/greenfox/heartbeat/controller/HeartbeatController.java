package com.greenfox.heartbeat.controller;

import com.greenfox.heartbeat.model.Heartbeat;
import com.greenfox.heartbeat.repository.HeartbeatRepository;
import com.greenfox.heartbeat.service.HeartbeatService;
import com.greenfox.rabbitmq.model.Consume;
import com.greenfox.rabbitmq.model.Send;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

  private Send send = new Send();
  private Consume consume = new Consume();
  private HeartbeatService heartbeatService;

  @Autowired
  public HeartbeatController(HeartbeatService heartbeatService,
      HeartbeatRepository heartbeatRepository) {
    this.heartbeatService = heartbeatService;
  }

  @GetMapping("/heartbeat")
  public Heartbeat validateMessage(HttpServletRequest request) throws Exception {
    return heartbeatService.getHeartBeat();
  }

  @GetMapping("/sendevent")
  public void sendEvent(HttpServletRequest request) throws Exception {
    send.dispatch("user-service.herokuapp.com", "hello");
    consume.consume("events");
  }
}
