package com.greenfox.register.model;

import com.greenfox.users.model.Link;

public class Data {
  private Link links;
  private String type;
  private Object attributes;

  public Data() {
  }

  public Data(Link links, String type, Object attributes) {
    this.links = links;
    this.type = type;
    this.attributes = attributes;
  }

  public Data(String type, Object attributes) {
    this.type = type;
    this.attributes = attributes;
  }

  public Link getLinks() {
    return links;
  }

  public void setLinks(Link links) {
    this.links = links;
  }

  public String getType() {
    return type;
  }

  public Object getAttributes() {
    return attributes;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }
}
