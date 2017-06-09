package com.greenfox.service;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import org.springframework.stereotype.Service;

@Service
public class HearthbeatService {

  public Hearthbeat getHearthBeat(HearthbeatRepository hearthbeatRepository) {
    if (hearthbeatRepository == null) {
      return new Hearthbeat("ok");
    } else if (hearthbeatRepository.count() == 0) {
      return new Hearthbeat("ok","error");
    } else {
      return new Hearthbeat("ok", "ok");
    }
  }
}
