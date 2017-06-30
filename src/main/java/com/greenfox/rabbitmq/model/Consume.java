package com.greenfox.rabbitmq.model;

import com.greenfox.rabbitmq.service.ConnectionSetter;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consume {
  public String receivedMessage;

  private final static String QUEUE_HEARTBEAT = "heartbeat";
  private final static String QUEUE_EVENT = "event";
  private final static String EXCHANGE_NAME = "exchange";

  public Consume() {
    this.receivedMessage = "";
  }

  public void consume(String queue) throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, false, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        receivedMessage = message;
        System.out.println("Received '" + message + "'");
      }
    };

    channel.basicConsume(queue, true, consumer);
    channel.close();
    connection.close();
  }


  public String getReceivedMessage() {
    return receivedMessage;
  }

  public void setReceivedMessage(String receivedMessage) {
    this.receivedMessage = receivedMessage;
  }

}