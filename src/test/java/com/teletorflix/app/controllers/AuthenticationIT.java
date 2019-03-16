package com.teletorflix.app.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.controllers.utils.HttpEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticationIT {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate rest;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http:/" + serverProperties.getAddress().toString() + ":" + localServerPort + "/oauth/token";
    }

    @Test
    void authorization_validCredentials_shouldReturnAccessToken() throws IOException {
        HttpEntity entity = HttpEntityFactory.getEntityWithHeaders(url);

//        String authorization = entity.getHeaders().get("Authorization").toString();

        String body = rest.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(body);
        String access_token = jsonNode.get("access_token").asText();

        assertThat(access_token).isNotNull();
    }


    @Test
    void authorization_invalidCredentials_shouldReturnBadCredentials() throws IOException {
        HttpEntity entity = HttpEntityFactory.createEntityWithBadCredentials(url);

        ResponseEntity<String> response = rest.exchange(url, HttpMethod.POST, entity, String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        String error = jsonNode.get("error").asText();
        String error_description = jsonNode.get("error_description").asText();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(error).isEqualTo("invalid_grant");
        assertThat(error_description).isEqualTo("Bad credentials");
    }


    @Test
    void authorization_validCredentialsAndInvalidClientData_shouldReturnUnauthorized() throws IOException {
        HttpEntity entity = HttpEntityFactory.createEntityWithInvalidClientData(url);

        ResponseEntity<String> response = rest.exchange(url, HttpMethod.POST, entity, String.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//        assertThat(error).isEqualTo("invalid_grant");
//        assertThat(error_description).isEqualTo("Bad credentials");
    }


}
