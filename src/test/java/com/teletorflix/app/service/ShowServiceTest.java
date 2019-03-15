package com.teletorflix.app.service;

import com.teletorflix.app.exceptions.*;
import com.teletorflix.app.model.*;
import com.teletorflix.app.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    private ShowService showService;

    private Show show;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private GenreService genreService;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private TvMazeService tvMazeService;

    @BeforeEach
    void setUp() {
        showService = new ShowServiceImpl(showRepository, genreService, scheduleService, tvMazeService);
        show = getShow();
    }

    @Test
    void getShow_ShowIdWithLasUpdatedLessThanTTL_ReturnsShowFromRepository() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusMinutes(40);
        show.setLastUpdate(lastUpdate);

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        Show returnedShow = showService.getShowById(show.getId());

        assertThat(returnedShow).isNotNull();
        verify(tvMazeService, times(0)).getShowById(show.getId());
        assertThat(returnedShow.getId()).isEqualTo(show.getId());
    }


    @Test
    void getShow_ShowIdWithLasUpdatedMoreThanTTLAndStatusNotEnded_ReturnsShowFromTvMazeService() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        Show show = getShow();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        when(tvMazeService.getShowById(show.getId())).thenReturn(show);
        when(scheduleService.getScheduleWithSavedDays(show.getSchedule())).thenReturn(getSchedule());
        when(genreService.saveIfAbsent(show.getGenres())).thenReturn(getGenres());
        when(showRepository.save(show)).thenReturn(show);

        Show returnedShow = showService.getShowById(show.getId());

        assertThat(returnedShow).isNotNull();
        verify(tvMazeService, times(1)).getShowById(show.getId());
    }

    @Test
    void getShow_ShowIdWithLasUpdatedMoreThanTTLAndStatusNotEnded_SaveReturnedShowFromTvMazeServiceInToDB() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        Show show = getShow();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        when(tvMazeService.getShowById(show.getId())).thenReturn(show);
        when(scheduleService.getScheduleWithSavedDays(show.getSchedule())).thenReturn(getSchedule());
        when(genreService.saveIfAbsent(show.getGenres())).thenReturn(getGenres());
        when(showRepository.save(show)).thenReturn(show);
        showService.getShowById(show.getId());

        verify(showRepository, times(1)).save(show);
    }

    @Test
    void getShow_ShowIdWithLastUpdateMoreThanTTLAndStatusEnded_ReturnsShowFromDB() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        Show returnedShow = showService.getShowById(show.getId());
        assertThat(returnedShow).isEqualTo(show);
    }


    @Test
    void getShow_ShowNotInDB_ReturnsShowFromTvMazeService() {
        when(showRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(tvMazeService.getShowById(show.getId())).thenReturn(show);
        when(scheduleService.getScheduleWithSavedDays(show.getSchedule())).thenReturn(getSchedule());
        when(genreService.saveIfAbsent(show.getGenres())).thenReturn(getGenres());
        when(showRepository.save(show)).thenReturn(show);
        Show returnedShow = showService.getShowById(show.getId());

        assertThat(returnedShow).isNotNull();
        verify(tvMazeService, times(1)).getShowById(show.getId());
        assertThat(returnedShow.getId()).isEqualTo(show.getId());
    }


    @Test
    void getShow_UpToDateShow_ShouldCallShowRepositoryGetById() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        showService.getShowById(show.getId());
        verify(showRepository).findById(anyInt());
    }

    @Test
    void getShow_ShowIdLessThanOne_ShouldThrowShowNotFoundException() {
        assertThrows(ShowNotFoundException.class, () -> showService.getShowById(0));
    }

    @Test
    void getShow_ValidShowIdNotInDBAndNotInTvMaze_ShouldThrowShowNotFoundException() {
        when(showRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(tvMazeService.getShowById(anyInt())).thenThrow(TvMazeShowNotFoundException.class);
        assertThrows(ShowNotFoundException.class, () -> showService.getShowById(10));
    }

    @Test
    void getSeason_ValidUpToDateShowAndValidSeasonNumberWithStatusNotEnded_ShouldReturnSeasonFromShowInDB() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        Season result = showService.getSeasonById(show.getId(), 3);
        assertThat(result).isEqualTo(three);
    }

    @Test
    void getSeason_ValidOutdatedShowAndValidSeasonNumberWithStatusNotEnded_ShouldReturnSeasonFromTvMazeService() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");
        show.setSeasons(List.of());
        List<Season> seasons = getSeasons();

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        when(tvMazeService.getShowById(show.getId())).thenReturn(show);
        when(scheduleService.getScheduleWithSavedDays(show.getSchedule())).thenReturn(getSchedule());
        when(genreService.saveIfAbsent(show.getGenres())).thenReturn(getGenres());

        when(tvMazeService.getSeasons(show.getId())).thenReturn(seasons);
        when(showRepository.save(show)).thenReturn(show);

        showService.getSeasonById(show.getId(), 3);


        InOrder inOrder = Mockito.inOrder(tvMazeService);
        inOrder.verify(tvMazeService).getShowById(show.getId());
        inOrder.verify(tvMazeService).getSeasons(show.getId());
    }

    @Test
    void getSeason_InvalidSeasonNumberOfUptoDateShowWithStatusNotEnded_ShoudlThrowSeasonNotFoundException() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        final int inavlidSeasonNumber = Integer.MAX_VALUE;
        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        when(tvMazeService.getSeasons(show.getId())).thenThrow(TvMazeResourceNotFoundException.class);

        assertThrows(TvMazeResourceNotFoundException.class, () -> showService.getSeasonById(show.getId(), inavlidSeasonNumber));
    }

    @Test
    void getSeason_InvalidSeasonNumberOfOutdatedShowWithStatusNotEnded_ShoudlThrowSeasonNotFoundException() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        show.setLastUpdate(lastUpdate);
        show.setStatus("Airing");

        final int inavlidSeasonNumber = Integer.MAX_VALUE;
        when(showRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(tvMazeService.getSeasons(show.getId())).thenReturn(getSeasons());
        when(tvMazeService.getShowById(show.getId())).thenReturn(show);
        when(scheduleService.getScheduleWithSavedDays(show.getSchedule())).thenReturn(getSchedule());
        when(genreService.saveIfAbsent(show.getGenres())).thenReturn(getGenres());
        when(showRepository.save(show)).thenReturn(show);

        assertThrows(SeasonNotFoundException.class, () -> showService.getSeasonById(show.getId(), inavlidSeasonNumber));
    }

    @Test
    void getSeason_ValidSeasonNumberOfUpToDateShowWithStatusEnded_ShouldReturnSeasonFromDB() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        Season result = showService.getSeasonById(show.getId(), 3);
        assertThat(result).isEqualTo(three);
    }

    @Test
    void getSeason_ValidSeasonNumberOfOutDatedShowWithStatusEnded_ShouldReturnSeasonFromDB() {
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));
        Season result = showService.getSeasonById(show.getId(), 3);
        assertThat(result).isEqualTo(three);
    }

    @Test
    void getEpisode_ValidShowWithValidSeasonAndValidEpisode_ShouldReturnEpisode() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        final int threeNumber = 3;
        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        final int oneNumber = 1;
        Episode one = three.getEpisodes().stream()
                .filter(episode -> episode.getNumber() == oneNumber)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        Episode episode = showService.getEpisode(show.getId(), threeNumber, oneNumber);

        assertThat(episode).isEqualTo(one);
    }

    @Test
    void getEpisode_ValidShowWithValidSeasonAndInvalidEpisode_ShouldThrowEpisodeNotFoundException() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        final int threeNumber = 3;
        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        final int invalidEpisodeNumber = Integer.MAX_VALUE;

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        assertThrows(EpisodeNotFoundException.class, () -> showService.getEpisode(show.getId(), threeNumber, invalidEpisodeNumber));
    }

    @Test
    void getLastEpisode_ValidShowWithValidSeason_ShouldReturnLastEpisode() {
        LocalDateTime lastUpdate = LocalDateTime.now();
        show.setLastUpdate(lastUpdate);
        show.setStatus("Ended");

        final int numberThree = 3;
        Season three = getSeasons().get(2);
        three.setEpisodes(getEpisodesFromLastSeason());

        Episode epThree = three.getEpisodes().stream()
                .filter(episode -> episode.getNumber() == numberThree)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        when(showRepository.findById(anyInt())).thenReturn(Optional.of(show));

        Episode episode = showService.getLastEpisode(show.getId());

        assertThat(episode).isEqualTo(epThree);
    }

    private Show getShow() {
        Schedule schedule = getSchedule();
        List<Genre> genres = getGenres();
        List<Season> seasons = getSeasons();
        //set seasons episodes
        seasons.get(2).setEpisodes(getEpisodesFromLastSeason());
        return Show.builder()
                .id(1)
                .name("Under the Dome")
                .tvMaze("http://www.tvmaze.com/shows/1/under-the-dome")
                .type("Scripted")
                .language("English")
                .genres(genres)
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
                .seasons(seasons)
                .build();
    }

    private Schedule getSchedule() {
        return Schedule.of(Set.of(ScheduleDay.of("Thursday")), LocalTime.of(22, 0));
    }

    private List<Genre> getGenres() {
        Genre drama = Genre.of("Drama");
        Genre science_fiction = Genre.of("Science-Fiction");
        Genre thriller = Genre.of("Thriller");
        return List.of(drama, science_fiction, thriller);
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
                .endDate(LocalDate.of(2015, 9, 10))
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
}