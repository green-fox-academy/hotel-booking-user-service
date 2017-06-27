package com.greenfox.rabbitmq.configuration;

import com.greenfox.rabbitmq.service.MockRabbitService;
import com.greenfox.rabbitmq.service.RabbitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class RabbitServiceConfiguration {

  @Bean
  public RabbitService rabbitService() {
    return new MockRabbitService();
  }
}
