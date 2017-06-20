package com.greenfox.controller;

import com.greenfox.UserServiceApplication;
import com.greenfox.repository.HeartbeatRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class RegisterControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private HeartbeatRepository heartbeatRepository;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void registerTest_withZeroUsers() throws Exception {
    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "     \"data\": {\n" +
                    "       \"type\": \"user\",\n" +
                    "       \"attributes\": {\n" +
                    "         \"email\": \"john.doe@example.org\",\n" +
                    "         \"password\": \"suchsecret\"\n" +
                    "       }\n" +
                    "     }\n" +
                    "   }"))
            .andExpect(status().isCreated())
            .andExpect(content().json("{\n" +
                    "     \"data\": {\n" +
                    "       \"type\": \"user\",\n" +
                    "       \"attributes\": {\n" +
                    "         \"id\": \"1\",\n" +
                    "         \"email\": \"john.doe@example.org\",\n" +
                    "         \"admin\": false,\n" +
                    "         \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJlbWFpbCI6ImpvaG4uZG9lQGV4YW1wbGUub3JnIiwiYWRtaW4iOmZhbHNlfQ.UK8Z1BNeHWvaFElWrrSxhO6oxTRaMW_66DO5yjkqOhM\"\n" +
                    "       }\n" +
                    "     }\n" +
                    "   }"));
  }
}
