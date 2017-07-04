package com.greenfox.register.model;

import com.greenfox.users.model.Link;

public class RequestData {
  private Data data;
  private Link links;

  public RequestData() {
  }

  public Link getLinks() {
    return links;
  }

  public void setLinks(Link links) {
    this.links = links;
  }

  public RequestData(Data data) {
    this.data = data;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }
}
