package com.greenfox.service;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.net.URI;
import java.net.URISyntaxException;

public class Send {

  private static URI rabbitmqSendUrl;
  private final static String QUEUE_NAME = "heartbeat";
  private static final String EXCHANGE_NAME = "log";

  public static void send() throws Exception {
    try {
      rabbitmqSendUrl = new URI(System.getenv("RABBITMQ_BIGWIG_TX_URL"));
    } catch(URISyntaxException e) {
      e.getStackTrace();
    }
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(rabbitmqSendUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitmqSendUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitmqSendUrl.getHost());
    factory.setPort(rabbitmqSendUrl.getPort());
    factory.setVirtualHost(rabbitmqSendUrl.getPath().substring(1));
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Send message";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println("Message sent: '" + message + "'");
    channel.close();
    connection.close();
  }

  public static URI getRabbitmqSendUrl() {
    return rabbitmqSendUrl;
  }

  public static void setRabbitmqSendUrl(URI rabbitmqSendUrl) {
    Send.rabbitmqSendUrl = rabbitmqSendUrl;
  }

  public static String getQueueName() {
    return QUEUE_NAME;
  }

  public static String getExchangeName() {
    return EXCHANGE_NAME;
  }
}
