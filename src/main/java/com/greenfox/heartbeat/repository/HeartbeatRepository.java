package com.greenfox.heartbeat.repository;

import com.greenfox.heartbeat.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface HeartbeatRepository extends CrudRepository<Status, Boolean>{
}