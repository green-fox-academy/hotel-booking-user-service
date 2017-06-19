package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatService {

  public Heartbeat getHeartBeat(HeartbeatRepository heartbeatRepository) {
    return heartbeatRepository.count() == 0 ?
        new Heartbeat("ok", "error") :
        new Heartbeat("ok", "ok");
  }
}
