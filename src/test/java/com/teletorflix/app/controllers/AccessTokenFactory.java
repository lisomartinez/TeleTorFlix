package com.teletorflix.app.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.Config.OauthAuthorizationServer;
import com.teletorflix.app.repository.UserRepository;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class AccessTokenFactory {

    private static final String OAUTH_URL = "/oauth/token";

    private ServerProperties serverProperties;

    private ObjectMapper objectMapper;

    public AccessTokenFactory(ServerProperties serverProperties, ObjectMapper objectMapper) {
        this.serverProperties = serverProperties;
        this.objectMapper = objectMapper;
    }

    public String createValidTokenMock(UserRepository userRepository, MockMvc mockMvc) throws Exception {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(UserFactory.createValidUser()));
        Map.Entry<String, String> userAndPassword = UserFactory.plainPasswordByValidUser();
        String username = userAndPassword.getKey();
        String plainPassword = userAndPassword.getValue();

        String body = "username=" + username + "&password=" + plainPassword + "&grant_type=password";

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .content(body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(httpBasic(OauthAuthorizationServer.CLIEN_ID, OauthAuthorizationServer.SECRET_PLAIN))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    public String createExpiredToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTI2MDk5MCwidXNlcl9uYW1lIjoiSm9obiIsImF1dGhvcml0aWVzIjpbIlJvbGVfQVVUSEVOVElDQVRFRCIsIlJvbGVfQURNSU4iXSwianRpIjoiY2Q0MjQyMDktZDhmNy00OWNjLWFkZTQtM2YyOGUzMjg4OTk2IiwiY2xpZW50X2lkIjoidHRmLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.vyjETkZG8Zu6GQYuToD6YL-T8YcoANhhkh5TT3pEY-Y";
    }

    public String createInvalidClientIdToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTI2MDk5MCwidXNlcl9uYW1lIjoiSm9obiIsImF1dGhvcml0aWVzIjpbIlJvbGVfQVVUSEVOVElDQVRFRCIsIlJvbGVfQURNSU4iXSwianRpIjoiY2Q0MjQyMDktZDhmNy00OWNjLWFkZTQtM2YyOGUzMjg4OTk2IiwiY2xpZW50X2lkIjoidGZmLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.zpLr5_2fYlAnBiH3wJZBEu_EGrHWBGk85Eri9OJfMzA";
    }

    public String createInvalidSignatureToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTI2MDk5MDMsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJSb2xlX0FVVEhFTlRJQ0FURUQiLCJSb2xlX0FETUlOIl0sImp0aSI6ImNkNDI0MjA5LWQ4ZjctNDljYy1hZGU0LTNmMjhlMzI4ODk5NiIsImNsaWVudF9pZCI6InR0Zi1jbGllbnQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.4e40eo-DY8V-cd9d5H7XM9Iaqzl-zhvPI9RWbW984k4";
    }

    public String createToken() throws IOException {
        String url = serverProperties.getAddress().toString() + serverProperties.getPort().toString() + "/oauth/token";
        HttpEntity entity = HttpEntityFactory.getEntityWithHeaders(url);

        RestTemplate rest = new RestTemplate();
        String body = rest.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(body);
        return jsonNode.get("access_token").asText();

    }
}
