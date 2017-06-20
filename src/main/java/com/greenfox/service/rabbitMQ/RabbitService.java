package com.greenfox.service.rabbitMQ;

import org.springframework.stereotype.Service;

@Service
public class RabbitService {

  public RabbitService() {
  }

  public boolean rabbitMonitoring() throws Exception {
    Send send = new Send();
    Consume consume = new Consume();
    send.send("monitoring message");
    consume.consume();
    String received = consume.getReceivedMessage();
    return (received.equals("monitoring message"));
  }
}
