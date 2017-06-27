package com.greenfox.heartbeat;

import com.greenfox.model.Yondu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YounduController {

  Yondu yondu;

  @Autowired
  public YounduController() {
    yondu = new Yondu();
  }

  @GetMapping("/yondu")
  public Yondu yondu() {
    return yondu;
  }
}
