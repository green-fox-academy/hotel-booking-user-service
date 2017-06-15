package com.greenfox.configuration;

import com.greenfox.service.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class EndPointLoggerConfiguration extends WebMvcConfigurerAdapter {

  LoggerInterceptor loggerInterceptor;

  @Autowired
  public EndPointLoggerConfiguration() {
    loggerInterceptor = new LoggerInterceptor();
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggerInterceptor)
            .addPathPatterns("/*");
  }
}
