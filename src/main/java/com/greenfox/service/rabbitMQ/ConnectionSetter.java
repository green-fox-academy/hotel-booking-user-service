package com.greenfox.service.rabbitMQ;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Profile("default")
public class ConnectionSetter {
  private static URI rabbitmqUrl;
  ConnectionFactory factory;

  public ConnectionSetter() {
    try {
      rabbitmqUrl = new URI(System.getenv("RABBITMQ_BIGWIG_URL"));
    } catch (URISyntaxException e) {
      e.getStackTrace();
    }
    this.factory = new ConnectionFactory();
  }

  public static void setRabbitmqUrl(URI rabbitmqUrl) {
    ConnectionSetter.rabbitmqUrl = rabbitmqUrl;
  }

  public ConnectionFactory getFactory() {
    return factory;
  }

  public void setFactory() {
    factory.setUsername(rabbitmqUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitmqUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitmqUrl.getHost());
    factory.setPort(rabbitmqUrl.getPort());
    factory.setVirtualHost(rabbitmqUrl.getPath().substring(1));
  }
}
