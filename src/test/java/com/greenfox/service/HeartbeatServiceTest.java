package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

public class HeartbeatServiceTest {

  HeartbeatRepository heartbeatRepository;

  @Before
  public void setUp() throws Exception {
    heartbeatRepository = Mockito.mock(HeartbeatRepository.class);
  }

  @Test
  public void getHeartBeatForDatabaseError() throws Exception {
    HeartbeatService heartbeatService = new HeartbeatService();
    Mockito.when(heartbeatRepository.count()).thenReturn(0L);
    assertTrue(new HeartbeatService().getHearthBeat(heartbeatRepository).getStatus().equals(new Heartbeat("ok", "error").getStatus()));
    assertTrue(new HeartbeatService().getHearthBeat(heartbeatRepository).getDatabase().equals(new Heartbeat("ok", "error").getDatabase()));
  }

  @Test
  public void getHeartBeatForDatabaseOk() throws Exception {
    HeartbeatService heartbeatService = new HeartbeatService();
    Mockito.when(heartbeatRepository.count()).thenReturn(1L);
    assertTrue(new HeartbeatService().getHearthBeat(heartbeatRepository).getStatus().equals(new Heartbeat("ok", "ok").getStatus()));
    assertTrue(new HeartbeatService().getHearthBeat(heartbeatRepository).getStatus().equals(new Heartbeat("ok", "ok").getStatus()));
  }

  @After
  public void tearDown() throws Exception {
  }

}