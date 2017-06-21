package com.greenfox.service.rabbitMQ;

import com.google.gson.Gson;
import com.greenfox.model.Event;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

  private final static String QUEUE_HEARTBEAT = "heartbeat";
  private final static String QUEUE_EVENT = "event";
  private final static String EXCHANGE_NAME = "exchange";

  public Send() {
  }

  public void send(String message) throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    channel.basicPublish(EXCHANGE_NAME, QUEUE_HEARTBEAT, null, message.getBytes("UTF-8"));
    System.out.println("Message sent: '" + message + "'");
  }

  public String dispatch(String hostname, String message) throws Exception {
    ConnectionSetter connectionSetter = new ConnectionSetter();
    connectionSetter.setFactory();
    Connection connection = connectionSetter.getFactory().newConnection();
    Channel channel = connection.createChannel();
    Gson gson = new Gson();
    String eventJson = gson.toJson(new Event(hostname, message));
    channel.basicPublish(EXCHANGE_NAME, QUEUE_EVENT, null, eventJson.getBytes());
    return eventJson;
  }

}
