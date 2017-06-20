package com.greenfox.model;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import org.junit.Test;

public class EventTest {

  @Test
  public void dispatch() throws Exception {
    Gson gson = new Gson();
    String eventJson = gson.toJson(new Event(LocalDateTime.now().withNano(0),"user-service.herokuapp.com", "hello"));
    assertEquals(eventJson,Event.dispatch("user-service.herokuapp.com", "hello"));
  }
}