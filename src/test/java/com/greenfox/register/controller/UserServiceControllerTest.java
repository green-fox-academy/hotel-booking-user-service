package com.greenfox.register.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.google.gson.JsonObject;
import com.greenfox.UserServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@WebAppConfiguration
@EnableWebMvc
@ActiveProfiles("test")
public class UserServiceControllerTest {

  private MockMvc mockMvc;
  private String jsonInput;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    JsonObject jobject = new JsonObject();
    JsonObject data = new JsonObject();
    JsonObject attributes = new JsonObject();
    jobject.add("data",data);
    data.addProperty("type","user");
    data.add("attributes", attributes);
    attributes.addProperty("email", "dombo.peter@example.com");
    attributes.addProperty("password","suchsecret");
    jsonInput = jobject.toString();
  }

  @Test
  public void saveAccountToDatabase() throws Exception {
    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonInput))
        .andExpect(status().isConflict());
  }

  @Test
  public void authenticateAccount() throws Exception {
  }
}