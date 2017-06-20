package com.greenfox.model;

import com.google.gson.Gson;
import java.time.LocalDateTime;

public class Event {
  private LocalDateTime time;
  private String hostname;
  private String message;

  public Event() {
  }

  public Event(String hostname, String message) {
    this.time = LocalDateTime.now().withNano(0);
    this.hostname = hostname;
    this.message = message;
  }

  public Event(LocalDateTime time, String hostname, String message) {
    this.time = time;
    this.hostname = hostname;
    this.message = message;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static String dispatch(String hostname, String message) {
    Gson gson = new Gson();
    String eventJson = gson.toJson(new Event(hostname, message));
    return eventJson;
  }
}
