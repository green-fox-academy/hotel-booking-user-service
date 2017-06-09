package com.greenfox.repository;

import com.greenfox.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface HearthbeatRepository extends CrudRepository<Status, Boolean>{
}