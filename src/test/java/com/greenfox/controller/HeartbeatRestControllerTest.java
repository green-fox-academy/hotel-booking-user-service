package com.greenfox.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.greenfox.UserServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

public class HeartbeatRestControllerTest {

  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = UserServiceApplication.class)
  @WebAppConfiguration
  @EnableWebMvc
  public class MessageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
      this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void GetHearbeatOK() throws Exception {
      mockMvc.perform(get("/heartbeat"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.status").value("ok"))
          .andExpect(jsonPath("$.database").value("ok"));
    }
  }
}
