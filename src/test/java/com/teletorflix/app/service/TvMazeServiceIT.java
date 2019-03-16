package com.teletorflix.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.dtos.JsonTestFiles;
import com.teletorflix.app.dtos.TvMazeEpisodeDto;
import com.teletorflix.app.dtos.TvMazeShowDto;
import com.teletorflix.app.exceptions.TvMazeShowNotFoundException;
import com.teletorflix.app.model.Episode;
import com.teletorflix.app.model.Schedule;
import com.teletorflix.app.model.ScheduleDay;
import com.teletorflix.app.model.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")
class TvMazeServiceIT {


    @Autowired
    private TvMazeService tvMazeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void FetchById_ValidId_ReturnShowJson() throws IOException {
        File showDeserialize = JsonTestFiles.getShow();
        TvMazeShowDto expectedDto = objectMapper.readValue(showDeserialize, TvMazeShowDto.class);
        Show expected = modelMapper.map(expectedDto, Show.class);

        Show show = tvMazeService.getShowById(1);
        assertThat(show).isEqualTo(expected);
    }

    @Test
    void FetchById_ValidIdScheduleId_ReturnShowJson() throws IOException {
        File showDeserialize = JsonTestFiles.getShow();
        TvMazeShowDto expectedDto = objectMapper.readValue(showDeserialize, TvMazeShowDto.class);
        Schedule dto = modelMapper.map(expectedDto.getSchedule(), Schedule.class);
        assertThat(dto.getDays()).containsExactly(ScheduleDay.of("Thursday"));
        Show expected = modelMapper.map(expectedDto, Show.class);

        Show show = tvMazeService.getShowById(1);
        assertThat(show.getId()).isEqualTo(expected.getId());
        assertThat(show.getName()).isEqualTo(expected.getName());
        assertThat(show.getGenres()).containsExactlyInAnyOrderElementsOf(expected.getGenres());
        assertThat(show.getLastUpdate()).isEqualTo(expected.getLastUpdate());
        assertThat(show.getSchedule().getDays()).containsExactly(ScheduleDay.of("Thursday"));
    }

    @Test
    void FetchById_InvalidID_ThrowsShowNotFoundException() {
        assertThrows(TvMazeShowNotFoundException.class, () -> tvMazeService.getShowById(0));
    }

    @Test
    void getSeason_ValidShowIdAndValisSeasonId_ShouldReturnEpisodes() throws IOException {
        File episodes = JsonTestFiles.getSeason6233Episodes();
        List<TvMazeEpisodeDto> episodeDtoList = objectMapper.readValue(episodes, new TypeReference<List<TvMazeEpisodeDto>>() {
        });
        assertThat(episodeDtoList.size()).isEqualTo(13);

        List<Episode> expectedList = episodeDtoList.stream().map(ep -> modelMapper.map(ep, Episode.class)).collect(Collectors.toList());

        List<Episode> episodeList = tvMazeService.getEpisodes(6233);

        assertThat(episodeList).containsExactlyElementsOf(expectedList);
    }
}