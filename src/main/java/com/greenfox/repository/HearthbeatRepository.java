package com.greenfox.repository;

import com.greenfox.model.Heartbeat;
import org.springframework.data.repository.CrudRepository;

public interface HearthbeatRepository extends CrudRepository<Heartbeat, Long>{

}