package com.greenfox.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

public class Consume {

  private final static String QUEUE_NAME = "heartbeat";

  public String consume() throws Exception {
    final String[] receivedMessage = {""};
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        receivedMessage[0] = message;
        System.out.println("Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
    return receivedMessage[0];
  }

  public static String getQueueName() {
    return QUEUE_NAME;
  }

}