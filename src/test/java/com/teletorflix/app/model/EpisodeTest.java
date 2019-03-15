package com.teletorflix.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class EpisodeTest {

    @Test
    @DisplayName("EpisodeBuilder returns Episode instance")
    void EpisodeBuilder_returnsInstance() {
        Episode episode = Episode.builder()
                .id(1)
                .name("Pilot")
                .number(1)
                .airDate(LocalDate.of(2016, 6, 24))
                .airTime(LocalTime.of(22, 0))
                .runtime(60)
                .image("http://static.tvmaze.com/uploads/images/original_untouched/1/4388.jpg")
                .tvMaze("http://www.tvmaze.com/episodes/1/under-the-dome-1x01-pilot")
                .summary("When the residents of Chester's Mill find themselves trapped under a massive " +
                        "transparent dome with no way out, they struggle to survive as resources rapidly " +
                        "dwindle and panic quickly escalates.")
                .build();

        assertThat(episode.getId()).isEqualTo(1);
        assertThat(episode.getName()).isEqualTo("Pilot");
        assertThat(episode.getNumber()).isEqualTo(1);
        assertThat(episode.getAirdate()).isEqualTo(LocalDate.of(2016, 6, 24));
        assertThat(episode.getAirtime()).isEqualTo(LocalTime.of(22, 0));
        assertThat(episode.getRuntime()).isEqualTo(60);
        assertThat(episode.getImage()).isEqualTo("http://static.tvmaze.com/uploads/images/original_untouched/1/4388.jpg");
        assertThat(episode.getTvMaze()).isEqualTo("http://www.tvmaze.com/episodes/1/under-the-dome-1x01-pilot");
        assertThat(episode.getSummary()).isEqualTo("When the residents of Chester's Mill find themselves trapped under a massive " +
                "transparent dome with no way out, they struggle to survive as resources rapidly " +
                "dwindle and panic quickly escalates.");

    }
}