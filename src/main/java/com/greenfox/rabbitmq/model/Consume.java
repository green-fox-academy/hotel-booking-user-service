package com.greenfox.rabbitmq.model;

import com.greenfox.rabbitmq.service.ConnectionSetter;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consume {
  public String receivedMessage;

  public Consume() {
    this.receivedMessage = "";
  }

  public void consume(String queue) throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.getChannel().queueDeclare(queue, false, false, false, null);

    Consumer consumer = new DefaultConsumer(connectionSetter.getChannel()) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        receivedMessage = message;
        System.out.println("Received '" + message + "'");
      }
    };

    connectionSetter.getChannel().basicConsume(queue, true, consumer);
    connectionSetter.getChannel().close();
    connectionSetter.getConnection().close();
  }

  public String getReceivedMessage() {
    return receivedMessage;
  }
}