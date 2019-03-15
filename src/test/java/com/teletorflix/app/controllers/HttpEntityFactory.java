package com.teletorflix.app.controllers;

import com.teletorflix.app.Config.OauthAuthorizationServer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.Charset;
import java.util.Map;


public class HttpEntityFactory {
    public static HttpEntity getEntityWithHeaders(String url) {
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> requestBody = getBody(url);
        return new HttpEntity<>(requestBody, headers);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", createAuthHeader());
        return headers;
    }

    private static String createAuthHeader() {
        String auth = OauthAuthorizationServer.CLIEN_ID + ":" + OauthAuthorizationServer.SECRET_PLAIN;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }

    public static MultiValueMap<String, String> getBody(String url) {
        Map.Entry<String, String> userAndPassword = UserFactory.plainPasswordByValidUser();
        String username = userAndPassword.getKey();
        String plainPassword = userAndPassword.getValue();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", username);
        requestBody.add("password", plainPassword);
        return requestBody;
    }

}
