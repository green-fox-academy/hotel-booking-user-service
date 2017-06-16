package com.greenfox.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Receive {

  private static URI rabbitmqReceiveUrl;
  private final static String QUEUE_NAME = "heartbeat";
  private static final String EXCHANGE_NAME = "log";

  public void receive() throws Exception {
    try {
      rabbitmqReceiveUrl = new URI(System.getenv("RABBITMQ_BIGWIG_RX_URL"));
    } catch(URISyntaxException e) {
      e.getStackTrace();
    }
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(rabbitmqReceiveUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitmqReceiveUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitmqReceiveUrl.getHost());
    factory.setPort(rabbitmqReceiveUrl.getPort());
    factory.setVirtualHost(rabbitmqReceiveUrl.getPath().substring(1));
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println("Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }

  public static URI getRabbitmqReceiveUrl() {
    return rabbitmqReceiveUrl;
  }

  public static void setRabbitmqReceiveUrl(URI rabbitmqReceiveUrl) {
    Receive.rabbitmqReceiveUrl = rabbitmqReceiveUrl;
  }

  public static String getQueueName() {
    return QUEUE_NAME;
  }

  public static String getExchangeName() {
    return EXCHANGE_NAME;
  }
}
