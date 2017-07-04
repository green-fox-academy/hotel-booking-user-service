package com.greenfox.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Link {

  private String self;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String next;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String prev;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String last;
}
