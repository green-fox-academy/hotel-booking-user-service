package com.greenfox.service;

import com.greenfox.model.Heartbeat;
import com.greenfox.repository.HeartbeatRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class HeartbeatServiceTest {

  private HeartbeatRepository heartbeatRepository;

  @Before
  public void setUp() throws Exception {
    heartbeatRepository = Mockito.mock(HeartbeatRepository.class);
  }

  @Test
  public void getHeartBeatForDatabaseErrorTest() throws Exception {
    Mockito.when(heartbeatRepository.count()).thenReturn(0L);
    assertEquals(new HeartbeatService().getHeartBeat(heartbeatRepository).getDatabase(),
            new Heartbeat("ok").getDatabase());
  }

  @Test
  public void getHeartBeatForDatabaseOkTest() throws Exception {
    Mockito.when(heartbeatRepository.count()).thenReturn(1L);
    Heartbeat testHeartbeat = new Heartbeat("ok");
    testHeartbeat.setDatabase("ok");
    assertEquals(new HeartbeatService().getHeartBeat(heartbeatRepository).getDatabase(),
            testHeartbeat.getDatabase());
  }


  @Test
  public void getHeartbeatForQueueErrorTest() {
    assertEquals(new HeartbeatService().getHeartBeat(heartbeatRepository).getQueue(),
            new Heartbeat("ok").getQueue());
  }

  @Test
  public void getHeartbeatForQueueOkTest() {
    Heartbeat testHeartbeat = new Heartbeat("ok");
    testHeartbeat.setQueue("ok");
    assertEquals(new HeartbeatService().getHeartBeat(heartbeatRepository).getQueue(),
            testHeartbeat.getQueue());
  }

  @After
  public void tearDown() throws Exception {
  }

}