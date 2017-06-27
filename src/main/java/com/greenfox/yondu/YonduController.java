package com.greenfox.yondu;

import com.greenfox.yondu.Yondu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YonduController {

  Yondu yondu;

  @Autowired
  public YonduController() {
    yondu = new Yondu();
  }

  @GetMapping("/yondu")
  public Yondu yondu() {
    return yondu;
  }
}
