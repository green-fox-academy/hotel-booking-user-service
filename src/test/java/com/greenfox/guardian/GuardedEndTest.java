package com.greenfox.guardian;

import com.greenfox.UserServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@WebAppConfiguration
@EnableWebMvc
@ActiveProfiles("test")
public class GuardedEndTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;


  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void guardedEndpointTest_withValidTokenInHeader() throws Exception {
    mockMvc.perform(get("/user/1")
            .header("Authorization", "Bearer " + "validToken"))
            .andExpect(status().isCreated())
            .andExpect(content().json("{\n" +
                    "     \"data\": {\n" +
                    "       \"type\": \"user\",\n" +
                    "       \"attributes\": {\n" +
                    "         \"id\": \"1\",\n" +
                    "         \"email\": \"john.doe@example.org\",\n" +
                    "         \"admin\": false,\n" +
                    "         \"token\": \"validToken\"\n" +
                    "       }\n" +
                    "     }\n" +
                    "   }"));
  }

  @Test
  public void guardedEndpointTest_withInvalidTokenInHeader() throws Exception {
    mockMvc.perform(get("/user/1")
            .header("Authorization", "Bearer " + "invalidToken"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().json("{\n" +
                    "     \"errors\": [{\n" +
                    "       \"status\": \"401\",\n" +
                    "       \"title\": \"Unauthorized\",\n" +
                    "       \"detail\": \"No token is provided\"\n" +
                    "     }]\n" +
                    "   }"));
  }

  @Test
  public void guardedEndpointTest_withoutHeader() throws Exception {
    mockMvc.perform(get("/user/1"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().json("{\n" +
                    "     \"errors\": [{\n" +
                    "       \"status\": \"401\",\n" +
                    "       \"title\": \"Unauthorized\",\n" +
                    "       \"detail\": \"No token is provided\"\n" +
                    "     }]\n" +
                    "   }"));
  }
}
