package com.teletorflix.app.controllers.utils;

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

    private static HttpHeaders getHttpHeaders(String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", createAuthHeader(clientId, clientSecret));
        return headers;
    }

    public static HttpEntity createEntityWithBadCredentials(String url) {
        HttpHeaders headers = getHttpHeaders();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", "invalid");
        requestBody.add("password", "invalid");

        return new HttpEntity<>(requestBody, headers);
    }

    public static HttpEntity createEntityWithInvalidClientData(String url) {
        MultiValueMap<String, String> requestBody = getBody(url);
        HttpHeaders headers = getHttpHeaders("invalidClientId", "invalidClientSecret");
        return new HttpEntity<>(requestBody, headers);
    }

    private static String createAuthHeader() {
        String auth = OauthAuthorizationServer.CLIEN_ID + ":" + OauthAuthorizationServer.SECRET_PLAIN;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }

    private static String createAuthHeader(String clientId, String clientSecret) {
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }

    private static MultiValueMap<String, String> getBody(String url) {
        Map.Entry<String, String> userAndPassword = UserFactory.plainPasswordByValidUser();
        String username = userAndPassword.getKey();
        String plainPassword = userAndPassword.getValue();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", username);
        requestBody.add("password", plainPassword);
        return requestBody;
    }

    public static HttpEntity createEntityWithHeaderWithAccesToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }

}
