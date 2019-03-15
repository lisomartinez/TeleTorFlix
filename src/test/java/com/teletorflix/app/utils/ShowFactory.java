package com.teletorflix.app.utils;

import com.teletorflix.app.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ShowFactory {

    public static Show getShowBase() {
        return Show.builder()
                .id(1)
                .name("Under the Dome")
                .tvMaze("http://www.tvmaze.com/shows/1/under-the-dome")
                .type("Scripted")
                .language("English")
                .runtime(60)
                .premiered(LocalDate.of(2013, 6, 24))
                .officialSite("http://www.cbs.com/shows/under-the-dome/")
                .imdb("https://www.imdb.com/title/tt1553656")
                .image("http://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg")
                .summary("Under the Dome is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.")
                .build();
    }

    public static Show getShowWithGenreAndSchedule(LocalDateTime lastUpdate, String status) {
        Show show = getShowBase();
        show.setSchedule(getScheduleWithId());
        show.setGenres(getGenresWithId());
        show.setLastUpdate(lastUpdate);
        show.setStatus(status);
        return show;
    }

    private static List<Genre> getGenresWithId() {
        Genre drama = new Genre(1, "Drama");
        Genre science_fiction = new Genre(2, "Science-Fiction");
        Genre thriller = new Genre(3, "Thriller");
        return List.of(drama, science_fiction, thriller);
    }

    private static Schedule getScheduleWithId() {
        return new Schedule(1, Set.of(new ScheduleDay(4, "Thursday")), LocalTime.of(22, 0));
    }

    public static Show getShowComplete(LocalDateTime lastUpdate, String status) {
        Show show = getShowBase();
        show.setSchedule(getSchedule());
        show.setGenres(getGenres());
        show.setLastUpdate(lastUpdate);
        show.setStatus(status);

        List<Season> seasons = getSeasons();
        //set seasons episodes
        seasons.get(2).setEpisodes(getEpisodesFromLastSeason());
        show.setSeasons(seasons);

        return show;
    }

    public static Show getShowUptodateAiring() {
        return getShowComplete(LocalDateTime.now(), "Airing");
    }

    public static Show getShowUptodateEnded() {
        return getShowComplete(LocalDateTime.now(), "Ended");
    }

    public static Show getShowOutdatedAiring() {
        return getShowComplete(LocalDateTime.now().minusHours(2), "Airing");
    }

    public static Show getShowOutdatedEnded() {
        return getShowComplete(LocalDateTime.now().minusHours(2), "Ended");
    }

    public static Schedule getSchedule() {
        return Schedule.of(Set.of(ScheduleDay.of("Thursday")), LocalTime.of(22, 0));
    }

    public static List<Genre> getGenres() {
        Genre drama = Genre.of("Drama");
        Genre science_fiction = Genre.of("Science-Fiction");
        Genre thriller = Genre.of("Thriller");
        return List.of(drama, science_fiction, thriller);
    }

    public static List<Season> getSeasons() {
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
                .endDate(LocalDate.of(2015, 9, 10))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60942.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/2/under-the-dome-season-2")
                .summary("N/A")
                .build();

        return List.of(seasonOne, seasonTwo, seasonThree);
    }

    public static Season getLastSeasonWithEpisodes() {
        return Season.builder()
                .id(3)
                .number(3)
                .episodeOrder(13)
                .premiereDate(LocalDate.of(2015, 6, 25))
                .endDate(LocalDate.of(2015, 9, 10))
                .image("http://static.tvmaze.com/uploads/images/original_untouched/24/60942.jpg")
                .tvMaze("http://www.tvmaze.com/seasons/2/under-the-dome-season-2")
                .summary("N/A")
                .episodes(getEpisodesFromLastSeason())
                .build();
    }

    public static List<Episode> getEpisodesFromLastSeason() {
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
}
