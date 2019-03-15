package com.teletorflix.app.functionalTests;

import com.teletorflix.app.controllers.HttpEntityFactory;
import com.teletorflix.app.controllers.ShowController;
import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;
import com.teletorflix.app.utils.ShowFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ShowControllerFunctionalTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private ServerProperties serverProperties;

    @Test
    void getShow_validShowId_ShouldReturnShow() {


        String url = serverProperties.getAddress().toString() + serverProperties.getPort().toString() + ShowController.SHOWS + "/1";
        HttpEntity entity = HttpEntityFactory.getEntityWithHeaders(url);

        Show expected = ShowFactory.getShowWithGenreAndSchedule(LocalDateTime.now(), "Ended");
        Show result = rest.exchange(url, HttpMethod.GET, entity, Show.class).getBody();
        expected.setLastUpdate(result.getLastUpdate());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getShow_ShowIdEqualsToZero_ShouldReturnStatusNotFound() {
        ResponseEntity<String> response = rest.getForEntity(ShowController.SHOWS + "/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getShow_InvalidShowId_ShouldReturnStatusNotFound() {
        ResponseEntity<String> response = rest.getForEntity(ShowController.SHOWS + "/" + Integer.MAX_VALUE, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getShowCalledTwoTimes_validShowId_ShouldReturnLastUpdateOfFirstShow() {
        Show expected = ShowFactory.getShowWithGenreAndSchedule(LocalDateTime.now(), "Ended");
        Show result = rest.getForObject(ShowController.SHOWS + "/1", Show.class);
        expected.setLastUpdate(LocalDateTime.of(result.getLastUpdate().toLocalDate(), result.getLastUpdate().toLocalTime()));

        assertThat(result.getLastUpdate()).isEqualTo(expected.getLastUpdate());
        Show result2 = rest.getForObject(ShowController.SHOWS + "/1", Show.class);
        assertThat(result2.getLastUpdate()).isEqualTo(expected.getLastUpdate());
    }

    @Test
    void getSeason_ValidShowIdAndValidSeasonId() {
        Season seasonThree = ShowFactory.getLastSeasonWithEpisodes();
        Season result = rest.getForObject(ShowController.SHOWS + "/1" + ShowController.SEASONS + "/3", Season.class);
        assertThat(result).isEqualTo(seasonThree);
    }
}
