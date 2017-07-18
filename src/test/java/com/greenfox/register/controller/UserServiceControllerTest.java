package com.greenfox.register.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.google.gson.JsonObject;
import com.greenfox.UserServiceApplication;
import com.greenfox.model.Account;
import com.greenfox.repository.AccountRepository;
import com.greenfox.service.JwtCreator;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mindrot.jbcrypt.BCrypt;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceControllerTest {

  private MockMvc mockMvc;
  private String jsonInput;

  @Autowired
  private JwtCreator jwtCreator;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    jsonInput = jsonForRegister();
  }

  @Test
  public void AAA_saveNewAccountToDatabase_ExpectCreated() throws Exception {
    accountRepository.deleteAll();
    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonInput))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.type").value("user"))
        .andExpect(jsonPath("$.data.attributes.id").value(1))
        .andExpect(jsonPath("$.data.attributes.email").value("john.doe@example.org"))
        .andExpect(jsonPath("$.data.attributes.admin").value(false))
        .andExpect(jsonPath("$.data.attributes.token").isNotEmpty());
  }

  @Test
  public void saveAlreadyRegisteredAccountToDatabase_ExpectConflict() throws Exception {
    accountRepository.deleteAll();
    accountRepository.save(new Account("john.doe@example.org",false,"token","suchsecret"));
    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonInput))
        .andExpect(status().isConflict());
  }



  @Test
  public void AAB_loginAuthenticadedUser_ExpectOK() throws Exception {
    accountRepository.deleteAll();
    String pw_hashed = BCrypt
        .hashpw("suchsecret", BCrypt.gensalt((Integer.parseInt(System.getenv("LOG_ROUNDS")))));
    accountRepository.save(new Account("john.doe@example.org",false,"token",pw_hashed));
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonInput))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.type").value("user"))
        .andExpect(jsonPath("$.data.attributes.id").value(2))
        .andExpect(jsonPath("$.data.attributes.email").value("john.doe@example.org"))
        .andExpect(jsonPath("$.data.attributes.admin").value(false))
        .andExpect(jsonPath("$.data.attributes.token").isNotEmpty());
  }

  @Test
  public void AAC_loginUnAuthorizedUser_ExpectUnauth() throws Exception {
    accountRepository.deleteAll();
    String pw_hashed = BCrypt
        .hashpw("WRONGsecret", BCrypt.gensalt((Integer.parseInt(System.getenv("LOG_ROUNDS")))));
    accountRepository.save(new Account("john.doe@example.org",false,"token",pw_hashed));
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonInput))
        .andExpect(status().isUnauthorized());
  }

  public String jsonForRegister() {
    JsonObject jobject = new JsonObject();
    JsonObject data = new JsonObject();
    JsonObject attributes = new JsonObject();
    jobject.add("data", data);
    data.addProperty("type","user");
    data.add("attributes", attributes);
    attributes.addProperty("email", "john.doe@example.org");
    attributes.addProperty("password","suchsecret");
    return jobject.toString();
  }
}