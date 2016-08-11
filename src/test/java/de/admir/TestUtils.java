package de.admir;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 11.08.2016
 */
@Component
public class TestUtils {
    public final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String host;

    private JSONObject spidermanJson;
    private JSONObject batmanJson;
    private JSONObject supermanJson;
    private JSONObject batmanBlogJson;

    {
        Map<String, String> spidermanMap = new HashMap<>();
        spidermanMap.put("firstName", "Peter");
        spidermanMap.put("lastName", "Parker");
        spidermanMap.put("username", "spiderman");
        spidermanMap.put("passwordHash", passwordEncoder.encode("maryjane"));
        spidermanJson = new JSONObject(spidermanMap);

        Map<String, String> batmanMap = new HashMap<>();
        batmanMap.put("firstName", "Bruce");
        batmanMap.put("lastName", "Wayne");
        batmanMap.put("username", "batman");
        batmanMap.put("passwordHash", passwordEncoder.encode("alfred"));
        batmanJson = new JSONObject(batmanMap);

        Map<String, String> supermanMap = new HashMap<>();
        supermanMap.put("firstName", "Clark");
        supermanMap.put("lastName", "Kent");
        supermanMap.put("username", "superman");
        supermanMap.put("passwordHash", passwordEncoder.encode("louis"));
        supermanJson = new JSONObject(supermanMap);

        Map<String, String> batmanBlogMap = new HashMap<>();
        batmanBlogMap.put("code", "bb");
        batmanBlogMap.put("name", "Batman's Blog");
        batmanBlogJson = new JSONObject(batmanBlogMap);
    }

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        this.host = "http://localhost:" + env.getProperty("server.port");
    }

    public String getHost() {
        return host;
    }

    public JSONObject getSpidermanJson() {
        return spidermanJson;
    }

    public JSONObject getBatmanJson() {
        return batmanJson;
    }

    public JSONObject getSupermanJson() {
        return supermanJson;
    }

    public JSONObject getBatmanBlogJson() {
        return batmanBlogJson;
    }
}
