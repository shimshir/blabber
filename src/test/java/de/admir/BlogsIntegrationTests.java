package de.admir;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BlogsIntegrationTests {
    @Autowired
    private TestUtils testUtils;

    @Test
    public void testCreateBlog() throws UnirestException {
        HttpResponse<String> unauthenticatedPostBlogResponse = Unirest.post(testUtils.getHost() + "/blogs")
                .header("Content-Type", "application/json")
                .body(testUtils.getBatmanBlogJson())
                .asString();

        assertThat(unauthenticatedPostBlogResponse.getStatus()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

        HttpResponse<String> authenticatedPostBlogResponse = Unirest.post(testUtils.getHost() + "/blogs")
                .header("Content-Type", "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeBase64String("admin:nimda".getBytes()))
                .body(testUtils.getBatmanBlogJson())
                .asString();

        assertThat(authenticatedPostBlogResponse.getStatus()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(authenticatedPostBlogResponse.getHeaders().getFirst(HttpHeaders.LOCATION)).isNotEmpty();

        HttpResponse<JsonNode> getBlogResponse = Unirest.get(authenticatedPostBlogResponse.getHeaders().getFirst(HttpHeaders.LOCATION)).asJson();

        assertThat(getBlogResponse.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getBlogResponse.getBody().getObject().getString("code")).isEqualTo("bb");
        assertThat(getBlogResponse.getBody().getObject().getString("name")).isEqualTo("Batman's Blog");
    }
}
