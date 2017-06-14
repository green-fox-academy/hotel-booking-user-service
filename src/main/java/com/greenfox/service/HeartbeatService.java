package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatService {

  public Heartbeat getHeartBeat(HeartbeatRepository heartbeatRepository) {
    if (heartbeatRepository.count() == 0) {
      return new Heartbeat("ok","error");
    } else {
      return new Heartbeat("ok", "ok");
    }
  }
}
