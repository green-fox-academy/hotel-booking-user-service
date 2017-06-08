package com.greenfox.repository;

import com.greenfox.model.Hearthbeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HearthbeatRepository extends CrudRepository<Hearthbeat, Long>{

}