package com.teletorflix.app.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TvMazeEpisodeDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getSeason_ValidShowIdAndValisSeasonId_ShouldReturnEpisodes() throws IOException {
        File episodes = JsonTestFiles.getSeason6233Episodes();
        List<TvMazeEpisodeDto> episodeDtoList = objectMapper.readValue(episodes, new TypeReference<List<TvMazeEpisodeDto>>() {
        });

        assertThat(episodeDtoList.size()).isEqualTo(13);
    }
}