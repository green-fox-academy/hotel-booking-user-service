package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatService {

  public Heartbeat getHeartBeat(HeartbeatRepository heartbeatRepository) {
    Heartbeat heartbeat = new Heartbeat("ok");
    if (heartbeatRepository.count() > 0) {
      heartbeat.setDatabase("ok");
    }
    if (true) {
      heartbeat.setQueue("ok");
    }
    return heartbeat;
  }
}
