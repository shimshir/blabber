package de.admir;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.admir.models.User;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApplicationTests {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String host;

    @Autowired
    private Environment env;

    @Before
    public void init() {
        this.host = "http://localhost:" + env.getProperty("server.port");
    }

    @Test
    public void testGetRoot() throws UnirestException {
        HttpResponse<String> response = Unirest.get(host).asString();
        System.out.println(response.getBody());
    }

    @Test
    public void testGetUsers() throws UnirestException {
        User spiderman = new User();
        spiderman.setFirstName("Peter");
        spiderman.setLastName("Parker");
        spiderman.setUsername("spiderman");
        spiderman.setPasswordHash(passwordEncoder.encode("maryjane"));

        Map<String, String> spidermanMap = new HashMap<>();
        spidermanMap.put("firstName", "Peter");
        spidermanMap.put("lastName", "Parker");
        spidermanMap.put("username", "spiderman");
        spidermanMap.put("passwordHash", passwordEncoder.encode("maryjane"));
        JSONObject spidermanJson = new JSONObject(spidermanMap);

        HttpResponse<String> postResponse = Unirest.post(host + "/users")
                .header("Content-Type", "application/json")
                .body(spidermanJson)
                .asString();

        HttpResponse<String> patchResponse = Unirest.patch(host + "/users/4")
                .header("Authorization", "Basic " + Base64.encodeBase64String("spiderman:maryjane".getBytes()))
                .header("Content-Type", "application/json")
                .body(new JSONObject("{\"firstName\": \"Peter Benjamin\"}"))
                .asString();

        HttpResponse<String> getResponse = Unirest.get(host + "/users/4")
                .header("Authorization", "Basic " + Base64.encodeBase64String("spiderman:maryjane".getBytes()))
                .asString();

        System.out.println(getResponse.getBody());
    }

}
