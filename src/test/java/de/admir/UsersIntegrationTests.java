package de.admir;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UsersIntegrationTests {
    @Autowired
    private TestUtils testUtils;


    @Test
    public void testGetRoot() throws UnirestException {
        HttpResponse<String> response = Unirest.get(testUtils.getHost()).asString();
        assertThat(response.getBody()).contains("/blogs", "/categories", "/comments", "/posts", "/users");
    }

    @Test
    public void testGetUsers() throws UnirestException {
        HttpResponse<String> unauthenticatedGetResponse = Unirest.get(testUtils.getHost() + "/users").asString();

        assertThat(unauthenticatedGetResponse.getStatus()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

        HttpResponse<String> authenticatedGetResponse = Unirest.get(testUtils.getHost() + "/users")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("admin:nimda".getBytes()))
                .asString();

        assertThat(authenticatedGetResponse.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(authenticatedGetResponse.getBody()).isNotEmpty();
    }

    @Test
    public void testCreateNewUser() throws UnirestException {
        HttpResponse<String> postResponse = Unirest.post(testUtils.getHost() + "/users")
                .header("Content-Type", "application/json")
                .body(testUtils.getSpidermanJson())
                .asString();

        assertThat(postResponse.getStatus()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(postResponse.getHeaders().get(HttpHeaders.LOCATION)).isNotEmpty();

        HttpResponse<String> getResponse = Unirest.get(postResponse.getHeaders().getFirst(HttpHeaders.LOCATION))
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("spiderman:maryjane".getBytes()))
                .asString();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getResponse.getBody()).contains("Peter", "Parker", "spiderman");
    }

    @Test
    public void testModifyUser() throws UnirestException {
        String batmanLocation = Unirest.post(testUtils.getHost() + "/users")
                .header("Content-Type", "application/json")
                .body(testUtils.getBatmanJson())
                .asString().getHeaders().getFirst(HttpHeaders.LOCATION);

        Unirest.post(testUtils.getHost() + "/users")
                .header("Content-Type", "application/json")
                .body(testUtils.getSupermanJson())
                .asString().getHeaders().getFirst(HttpHeaders.LOCATION);

        HttpResponse<String> unauthenticatedPatchResponse = Unirest.patch(batmanLocation)
                .header("Content-Type", "application/json")
                .body(new JSONObject("{\"firstName\": \"Dark\", \"lastName\": \"Knight\"}"))
                .asString();

        assertThat(unauthenticatedPatchResponse.getStatus()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

        HttpResponse<String> forbiddenPatchResponse = Unirest.patch(batmanLocation)
                .header("Content-Type", "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("superman:louis".getBytes()))
                .body(new JSONObject("{\"firstName\": \"Dark\", \"lastName\": \"Knight\"}"))
                .asString();

        assertThat(forbiddenPatchResponse.getStatus()).isEqualTo(HttpStatus.SC_FORBIDDEN);

        HttpResponse<String> authenticatedPatchResponse = Unirest.patch(batmanLocation)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("batman:alfred".getBytes()))
                .header("Content-Type", "application/json")
                .body(new JSONObject("{\"firstName\": \"Dark\", \"lastName\": \"Knight\"}"))
                .asString();

        assertThat(authenticatedPatchResponse.getStatus()).isEqualTo(HttpStatus.SC_NO_CONTENT);

        HttpResponse<JsonNode> getResponse = Unirest.get(batmanLocation)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("batman:alfred".getBytes()))
                .asJson();

        assertThat(getResponse.getBody().getObject().getString("firstName")).isEqualTo("Dark");
        assertThat(getResponse.getBody().getObject().getString("lastName")).isEqualTo("Knight");
    }
}
