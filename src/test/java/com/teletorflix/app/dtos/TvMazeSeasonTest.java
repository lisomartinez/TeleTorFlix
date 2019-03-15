package com.teletorflix.app.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDate;

public class TvMazeSeasonTest {
    private JacksonTester<TvMazeSeason> json;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void parseJson_ValidJson_ShouldReturnSeasonInstance() throws IOException {
        TvMazeSeason expected = getSeason();
        TvMazeSeason season = json.readObject(JsonTestFiles.getSeasonOne());
    }

    private TvMazeSeason getSeason() {
        return TvMazeSeason.builder()
                .id(1)
                .url("http://www.tvmaze.com/seasons/1/under-the-dome-season-1")
                .name("N/A")
                .episodes(13)
                .premiereDate(LocalDate.of(2013, 6, 24))
                .endDate(LocalDate.of(2013, 9, 16))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60941.jpg")
                .summary("N/A")
                .build();

    }
}
