package com.greenfox.service;

import org.springframework.stereotype.Service;

@Service
public class RabbitService {

  public RabbitService() {
  }

  public boolean rabbitMonitoring() throws Exception {
    Send send = new Send();
    Consume consume = new Consume();
    send.send("message");
    consume.consume();
    String received = consume.getReceivedMessage();
    if (received.equals(received)) {
      return true;
    } else {
      return false;
    }
  }
}
