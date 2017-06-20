package com.greenfox.service.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consume {
  public String receivedMessage;

  private final static String QUEUE_NAME = "heartbeat";

  public Consume() {
    this.receivedMessage = "";
  }

  public void consume() throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//    channel.queueDeclare("event", false, false, false, null);
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
    channel.basicConsume(QUEUE_NAME, true, consumer);
//    channel.basicConsume("event", true, consumer);
    channel.close();
    connection.close();
  }

  public static String getQueueName() {
    return QUEUE_NAME;
  }

  public String getReceivedMessage() {
    return receivedMessage;
  }

  public void setReceivedMessage(String receivedMessage) {
    this.receivedMessage = receivedMessage;
  }

}