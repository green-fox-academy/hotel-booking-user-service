package com.greenfox.service;

import com.greenfox.model.Hearthbeat;
import com.greenfox.repository.HearthbeatRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

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
    assertTrue(new HearthbeatService().getHearthBeat(hearthbeatRepository).getStatus().equals(new Hearthbeat("ok", "error").getStatus()));
    assertTrue(new HearthbeatService().getHearthBeat(hearthbeatRepository).getDatabase().equals(new Hearthbeat("ok", "error").getDatabase()));
  }

  @Test
  public void getHeartBeatForDatabaseOk() throws Exception {
    HearthbeatService hearthbeatService = new HearthbeatService();
    Mockito.when(hearthbeatRepository.count()).thenReturn(1L);
    assertTrue(new HearthbeatService().getHearthBeat(hearthbeatRepository).getStatus().equals(new Hearthbeat("ok", "ok").getStatus()));
    assertTrue(new HearthbeatService().getHearthBeat(hearthbeatRepository).getStatus().equals(new Hearthbeat("ok", "ok").getStatus()));
  }

  @After
  public void tearDown() throws Exception {
  }

}