package com.teletorflix.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SeasonTest {

    @Test
    @DisplayName("SeasonBuilder returns Season instance")
    void seasonBuilder_returnsInstance() {
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

        Season season = Season.builder()
                .id(1)
                .number(1)
                .episodeOrder(13)
                .premiereDate(LocalDate.of(2013, 6, 24))
                .endDate(LocalDate.of(2013, 9, 16))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60941.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/1/under-the-dome-season-1")
                .summary("N/A")
                .episodes(List.of(episode))
                .build();

        assertThat(season.getId()).isEqualTo(1);
        assertThat(season.getNumber()).isEqualTo(1);
        assertThat(season.getEpisodeOrder()).isEqualTo(13);
        assertThat(season.getPremiereDate()).isEqualTo(LocalDate.of(2013, 6, 24));
        assertThat(season.getEndDate()).isEqualTo(LocalDate.of(2013, 9, 16));
        assertThat(season.getImage()).isEqualTo("http://static.tvmaze.com/uploads/images/original_untouched/24/60941.jpg");
        assertThat(season.getTvMaze()).isEqualTo("http://www.tvmaze.com/seasons/1/under-the-dome-season-1");
        assertThat(season.getSummary()).isEqualTo("N/A");
        assertThat(season.getEpisodes()).isEqualTo(List.of(episode));

    }
}