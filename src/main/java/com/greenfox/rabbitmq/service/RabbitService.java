package com.greenfox.rabbitmq.service;

import com.greenfox.rabbitmq.model.Send;
import com.greenfox.rabbitmq.model.Consume;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

  public RabbitService() {
  }

  public boolean rabbitMonitoring() throws Exception {
    Send send = new Send();
    Consume consume = new Consume();
    send.send("monitoring message");
    consume.consume("heartbeat");
    String received = consume.getReceivedMessage();
    return (received.equals("monitoring message"));
  }
}
