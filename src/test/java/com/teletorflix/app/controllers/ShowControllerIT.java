package com.teletorflix.app.controllers;

import com.teletorflix.app.controllers.utils.AccessTokenFactory;
import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;
import com.teletorflix.app.utils.ShowFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ShowControllerIT {

    private String baseUrl;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private AccessTokenFactory tokenFactory;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        baseUrl = "http:/" + serverProperties.getAddress() + ":" + port;
    }

    @Test
    void getShow_validShowId_ShouldReturnShow() throws IOException {
        String url = baseUrl + ShowController.SHOWS + ShowController.SHOW_ID;
        HttpEntity entity = tokenFactory.entityWithAccessTokenHeader(baseUrl);

        Show expected = ShowFactory.getShowWithGenreAndSchedule(LocalDateTime.now(), "Ended");
        ResponseEntity<String> response = rest.exchange(url, GET, entity, String.class, 1);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getShow_ShowIdEqualsToZero_ShouldReturnStatusNotFound() throws IOException {
        String url = baseUrl + ShowController.SHOWS + ShowController.SHOW_ID;
        HttpEntity entity = tokenFactory.entityWithAccessTokenHeader(baseUrl);

        ResponseEntity<String> response = rest.exchange(url, GET, entity, String.class, 0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getShow_InvalidShowId_ShouldReturnStatusNotFound() throws IOException {
        String url = baseUrl + ShowController.SHOWS + ShowController.SHOW_ID;
        HttpEntity entity = tokenFactory.entityWithAccessTokenHeader(baseUrl);

        ResponseEntity<String> response = rest.exchange(url, GET, entity, String.class, Integer.MAX_VALUE);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getShowCalledTwoTimes_validShowId_ShouldReturnLastUpdateOfFirstShow() throws IOException {
        String url = baseUrl + ShowController.SHOWS + ShowController.SHOW_ID;
        HttpEntity entity = tokenFactory.entityWithAccessTokenHeader(baseUrl);


        Show expected = ShowFactory.getShowWithGenreAndSchedule(LocalDateTime.now(), "Ended");
        Show response = rest.exchange(url, GET, entity, Show.class, 1).getBody();


        expected.setLastUpdate(LocalDateTime.of(response.getLastUpdate().toLocalDate(), response.getLastUpdate().toLocalTime()));

        assertThat(response.getLastUpdate()).isEqualTo(expected.getLastUpdate());

        Show result2 = rest.exchange(url, GET, entity, Show.class, 1).getBody();
        assertThat(result2.getLastUpdate()).isEqualTo(expected.getLastUpdate());
    }

    @Test
    void getSeason_ValidShowIdAndValidSeasonId() throws IOException {

        String url = baseUrl + ShowController.SHOWS + ShowController.SHOW_ID +
                ShowController.SEASON + ShowController.SEASON_NUMBER;

        HttpEntity entity = tokenFactory.entityWithAccessTokenHeader(baseUrl);

        Season seasonThree = ShowFactory.getLastSeasonWithEpisodes();

        Season response = rest.exchange(url, GET, entity, Season.class, 1, 3).getBody();

        assertThat(response).isEqualTo(seasonThree);
    }


}
