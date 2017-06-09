package com.greenfox.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HearthbeatServiceTest {

  HearthbeatRepository hearthbeatRepository;

  @Before
  public void setUp() throws Exception {
    hearthbeatRepository = Mockito.mock(HearthbeatRepository.class);
  }

  @Test
  public void getHeartBeatForDatabaseError() throws Exception {
    HearthbeatService hearthbeatService = new HearthbeatService();
    Mockito.when(hearthbeatRepository.count()).thenReturn(0L);
    Hearthbeat hearthbeat = hearthbeatService.getHearthBeat(hearthbeatRepository);
    assertEquals(hearthbeat.getDatabase(),"error");
  }

  @Test
  public void getHeartBeatForDatabaseOk() throws Exception {
    HearthbeatService hearthbeatService = new HearthbeatService();
    Mockito.when(hearthbeatRepository.count()).thenReturn(1L);
    Hearthbeat hearthbeat = hearthbeatService.getHearthBeat(hearthbeatRepository);
    assertEquals(hearthbeat.getDatabase(),"ok");
  }

  @After
  public void tearDown() throws Exception {
  }

}