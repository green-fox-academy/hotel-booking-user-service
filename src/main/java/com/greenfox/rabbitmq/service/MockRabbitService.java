package com.greenfox.rabbitmq.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("test")
public class MockRabbitService extends RabbitService {

  private List<String> queue;

  public MockRabbitService() {
    queue = new ArrayList<>();
  }

  @Override
  public boolean rabbitMonitoring() {
    System.out.println("monitoring test version");
    return queue.isEmpty();
  }

  public void send() {
    queue.add("something is in the queue");
  }

  public void consume() {
    queue.clear();
  }
}
