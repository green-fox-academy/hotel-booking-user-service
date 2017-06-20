package com.greenfox.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

  private final static String QUEUE_NAME = "heartbeat";

  public Send() {
  }

  public void send(String message) throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println("Message sent: '" + message + "'");
    channel.close();
    connection.close();
  }

  public static String getQueueName() {
    return QUEUE_NAME;
  }
}
