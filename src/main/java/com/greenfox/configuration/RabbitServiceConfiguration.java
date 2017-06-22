package com.greenfox.configuration;

import com.greenfox.service.rabbitMQ.MockRabbitService;
import com.greenfox.service.rabbitMQ.RabbitService;
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
