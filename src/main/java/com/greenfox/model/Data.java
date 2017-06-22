package com.greenfox.model;

public class Data {
  private String type;
  private Object attributes;

  public Data() {
  }

  public Data(String type, Object attributes) {
    this.type = type;
    this.attributes = attributes;
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
