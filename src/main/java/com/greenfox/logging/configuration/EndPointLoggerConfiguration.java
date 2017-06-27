package com.greenfox.logging.configuration;

import com.greenfox.logging.service.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class EndPointLoggerConfiguration extends WebMvcConfigurerAdapter {

  private LoggerInterceptor loggerInterceptor;

  @Autowired
  public EndPointLoggerConfiguration() {
    loggerInterceptor = new LoggerInterceptor();
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggerInterceptor)
            .addPathPatterns("/*");
  }
}
