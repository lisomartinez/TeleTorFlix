package com.teletorflix.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ShowTest {

    @Test
    @DisplayName("Static Factory should return instance")
    void staticFactoryMethod_ValidFields_ReturnsInstance() {

        Schedule schedule = Schedule.of(Set.of(ScheduleDay.of("Thursday")), LocalTime.of(22, 0));
        Genre drama = Genre.of("Drama");
        Genre science_fiction = Genre.of("Science-Fiction");
        Genre thriller = Genre.of("Thriller");

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

        Show show = Show.builder()
                .id(1)
                .name("Under the Dome")
                .tvMaze("http://www.tvmaze.com/shows/1/under-the-dome")
                .type("Scripted")
                .language("English")
                .genres(List.of(drama, science_fiction, thriller))
                .status("Ended")
                .runtime(60)
                .premiered(LocalDate.of(2013, 6, 24))
                .officialSite("http://www.cbs.com/shows/under-the-dome/")
                .schedule(schedule)
                .imdb("https://www.imdb.com/title/tt1553656/")
                .image("http://static.tvmaze.com/uploads/images/original_untouched/0/1.jpg")
                .summary("Under the Dome is the story of a small town that is suddenly and inexplicably sealed off " +
                        "of the rest of the world by an enormous transparent dome. The town's inhabitants must " +
                        "deal with surviving the post-apocalyptic conditions while searching for answers about the " +
                        "dome, where it came of and if and when it will go away.")
                .lastUpdate(LocalDateTime.of(LocalDate.of(2001, 9, 11), LocalTime.of(11, 30)))
                .seasons(List.of(season))
                .build();

        assertThat(show.getId()).isEqualTo(1);
        assertThat(show.getName()).isEqualTo("Under the Dome");
        assertThat(show.getTvMaze()).isEqualTo("http://www.tvmaze.com/shows/1/under-the-dome");
        assertThat(show.getType()).isEqualTo("Scripted");
        assertThat(show.getLanguage()).isEqualTo("English");
        assertThat(show.getGenres()).isEqualTo(List.of(drama, science_fiction, thriller));
        assertThat(show.getStatus()).isEqualTo("Ended");
        assertThat(show.getRuntime()).isEqualTo(60);
        assertThat(show.getPremiered()).isEqualTo(LocalDate.of(2013, 6, 24));
        assertThat(show.getOfficialSite()).isEqualTo("http://www.cbs.com/shows/under-the-dome/");
        assertThat(show.getSchedule()).isEqualTo(schedule);
        assertThat(show.getImdb()).isEqualTo("https://www.imdb.com/title/tt1553656/");
        assertThat(show.getImage()).isEqualTo("http://static.tvmaze.com/uploads/images/original_untouched/0/1.jpg");
        assertThat(show.getSummary()).isEqualTo("Under the Dome is the story of a small town that is suddenly and inexplicably sealed off " +
                "of the rest of the world by an enormous transparent dome. The town's inhabitants must " +
                "deal with surviving the post-apocalyptic conditions while searching for answers about the " +
                "dome, where it came of and if and when it will go away.");
        assertThat(show.getLastUpdate()).isEqualTo(LocalDateTime.of(LocalDate.of(2001, 9, 11), LocalTime.of(11, 30)));
        assertThat(show.getSeasons()).isEqualTo(List.of(season));
    }
}