package com.teletorflix.app.functionalTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.controllers.HttpEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticationFunctionalTest {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int localServerPort;

    @Test
    void name() throws IOException {
        String url = "http:/" + serverProperties.getAddress().toString() + ":" + localServerPort + "/oauth/token";
        HttpEntity entity = HttpEntityFactory.getEntityWithHeaders(url);

        String authorization = entity.getHeaders().get("Authorization").toString();

        RestTemplate rest = new RestTemplate();
        String body = rest.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(body);
        String access_token = jsonNode.get("access_token").asText();

        assertThat(access_token).isNotNull();
    }
}
