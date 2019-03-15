package com.teletorflix.app.service;


import com.teletorflix.app.exceptions.ShowNotFoundException;
import com.teletorflix.app.model.*;
import com.teletorflix.app.repository.ScheduleDayRepository;
import com.teletorflix.app.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@Transactional
public class ShowServiceIT {
    private ShowService showService;

    @Autowired
    private ScheduleDayRepository scheduleDayRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TvMazeService tvMazeService;

    @BeforeEach
    void setUp() {
        showService = new ShowServiceImpl(showRepository, genreService, scheduleService, tvMazeService);
    }

    @Test
    void getShowById_ValidShowUpToDateShowIdWithStatusAiring_ReturnsShowFromDB() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusMinutes(20);
        Show expected = getShow(lastUpdate, "Airing");
        final int id = expected.getId();

        Show result = showService.getShowById(id);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getShowById_ValidShowOutdatedShowIdWithStatusAiring_ReturnsShowFromDB() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        Show expected = getShow(lastUpdate, "Airing");
        final int id = expected.getId();

        Show result = showService.getShowById(id);
        assertThat(result).isEqualTo(expected);
    }


    @Test
    void getShowById_InvalidShowId_ShouldThrowException() {
        assertThrows(ShowNotFoundException.class, () -> showService.getShowById(Integer.MAX_VALUE));
    }

    private Show getShow(LocalDateTime lastUpdate, String status) {
        Schedule schedule = getSchedule();
        List<Genre> genres = getGenres();
        List<Season> seasons = getSeasons();
        Show show = Show.builder()
                .id(1)
                .name("Under the Dome")
                .tvMaze("http://www.tvmaze.com/shows/1/under-the-dome")
                .type("Scripted")
                .language("English")
                .genres(genres)
                .status(status)
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
                .lastUpdate(lastUpdate)
                .seasons(seasons)
                .build();
        return showRepository.save(show);
    }

    private List<Season> getSeasons() {
        Season seasonOne = Season.builder()
                .id(1)
                .number(1)
                .episodeOrder(13)
                .premiereDate(LocalDate.of(2013, 6, 24))
                .endDate(LocalDate.of(2013, 9, 16))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60941.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/1/under-the-dome-season-1")
                .summary("N/A")
                .build();

        Season seasonTwo = Season.builder()
                .id(2)
                .number(2)
                .episodeOrder(13)
                .premiereDate(LocalDate.of(2014, 6, 30))
                .endDate(LocalDate.of(2014, 9, 22))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60942.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/2/under-the-dome-season-2")
                .summary("N/A")
                .build();

        Season seasonThree = Season.builder()
                .id(3)
                .number(3)
                .episodeOrder(13)
                .premiereDate(LocalDate.of(2015, 6, 25))
                .endDate(LocalDate.of(20154, 9, 10))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60942.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/2/under-the-dome-season-2")
                .summary("N/A")
                .build();

        return List.of(seasonOne, seasonTwo, seasonThree);
    }

    private List<Episode> getEpisodesFromLastSeason() {
        Episode eleven = Episode.builder()
                .id(142270)
                .name("Move On")
                .number(1)
                .airDate(LocalDate.of(2015, 6, 25))
                .airTime(LocalTime.of(22, 0))
                .runtime(60)
                .image("http://static.tvmaze.com/uploads/images/original_untouched/12/31233.jpg")
                .tvMaze("http://www.tvmaze.com/episodes/142270/under-the-dome-3x01-move-on")
                .summary("Season 3 begins with Chester's Mill residents appearing inside and outside the Dome following an evacuation into the tunnels beneath the town. Meanwhile, the Dome begins to reveal its ultimate agenda; and surprising alliances form as new residents emerge.")
                .build();

        Episode twelve = Episode.builder()
                .id(151048)
                .name("But I'm Not")
                .number(2)
                .airDate(LocalDate.of(2015, 6, 25))
                .airTime(LocalTime.of(22, 0))
                .runtime(60)
                .image("http://static.tvmaze.com/uploads/images/original_untouched/12/31234.jpg")
                .tvMaze("http://www.tvmaze.com/episodes/151048/under-the-dome-3x02-but-im-not")
                .summary(">Chester's Mill residents appear inside and outside the Dome following an exit into the tunnels beneath the town. Meanwhile, the Dome begins to reveal its ultimate agenda; and surprising alliances form as new residents emerge.")
                .build();

        Episode thirteen = Episode.builder()
                .id(151645)
                .name("Redux")
                .number(3)
                .airDate(LocalDate.of(2015, 9, 10))
                .airTime(LocalTime.of(22, 0))
                .runtime(60)
                .image("http://static.tvmaze.com/uploads/images/original_untouched/12/31939.jpg")
                .tvMaze("http://www.tvmaze.com/episodes/151645/under-the-dome-3x03-redux")
                .summary("The residents of Chester's Mill try to move on with their lives in the aftermath of their mysterious experience in the tunnels beneath town. Meanwhile, Big Jim suspects new residents Christine and Eva are keeping secrets concerning the Dome.")
                .build();

        return List.of(eleven, twelve, thirteen);
    }

    private Schedule getSchedule() {
        ScheduleDay thursday = scheduleDayRepository.findByDay("Thursday").get();
        return Schedule.of(Set.of(thursday), LocalTime.of(22, 0));
    }

    private List<Genre> getGenres() {
        Genre drama = Genre.of("Drama");
        Genre scienceFiction = Genre.of("Science_Fiction");
        Genre thriller = Genre.of("Thriller");
        return genreService.saveIfAbsent(List.of(drama, scienceFiction, thriller));
    }
}
