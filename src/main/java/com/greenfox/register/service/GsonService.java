package com.greenfox.register.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.greenfox.register.model.Credentials;
import org.springframework.stereotype.Service;

@Service
public class GsonService {

  public Credentials parseCredentials(String json) {
    JsonElement jelement = new JsonParser().parse(json);
    JsonObject data = jelement.getAsJsonObject();
    data = data.getAsJsonObject("data");
    JsonObject attributes = data.getAsJsonObject("attributes");
    String email = attributes.get("email").getAsString();
    String password = attributes.get("password").getAsString();
    return new Credentials(email,password);
  }

  public String createAccountJson(Long id, String email, boolean admin, String token) {
    JsonObject jobject = new JsonObject();
    JsonObject data = new JsonObject();
    JsonObject attributes = new JsonObject();
    jobject.add("data", data);
    data.addProperty("type","user");
    data.add("attributes", attributes);
    attributes.addProperty("id", id.toString());
    attributes.addProperty("email",email);
    System.out.println(email);
    attributes.addProperty("admin",admin);
    attributes.addProperty("token",token);
    return jobject.toString();
  }
}
