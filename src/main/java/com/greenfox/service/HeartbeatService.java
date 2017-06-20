package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import com.greenfox.service.rabbitMQ.RabbitService;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatService {

  public Heartbeat getHeartBeat(HeartbeatRepository heartbeatRepository) throws Exception {
    RabbitService rabbitService = new RabbitService();
    Heartbeat heartbeat = new Heartbeat("ok");
    if (heartbeatRepository.count() > 0) {
      heartbeat.setDatabase("ok");
    }
    if (rabbitService.rabbitMonitoring()) {
      heartbeat.setQueue("ok");
    }
    return heartbeat;
  }
}
