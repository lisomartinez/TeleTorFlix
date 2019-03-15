package com.teletorflix.app.service;

import com.teletorflix.app.exceptions.*;
import com.teletorflix.app.model.*;
import com.teletorflix.app.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class ShowServiceImpl implements ShowService {

    private ShowRepository showRepository;

    private GenreService genreService;

    private ScheduleService scheduleService;

    private TvMazeService tvMazeService;

    @Autowired
    public ShowServiceImpl(ShowRepository showRepository, GenreService genreService, ScheduleService scheduleService,
                           TvMazeService tvMazeService) {
        this.showRepository = showRepository;
        this.genreService = genreService;
        this.scheduleService = scheduleService;
        this.tvMazeService = tvMazeService;
    }

    @Override
    public Show getShowById(int id) {
        if (id < 1) {
            throw new ShowNotFoundException("Show id should be greater than or equal to 1");
        }

        Optional<Show> show = showRepository.findById(id);

        if (isShowUpToDate(show)) {
            return show.get();
        } else {
            Show tvMazeShow = getTvMazeShow(id);
            return showRepository.save(tvMazeShow);
        }
    }

    private boolean isShowUpToDate(Optional<Show> optionalShow) {
        Predicate<Show> isUpToDate = show -> isEnded(show) || lastUpdateTimeIsLessThanTTL(show);
        return optionalShow.filter(isUpToDate).isPresent();
    }

    private boolean isEnded(Show show) {
        return show.getStatus().equals("Ended");
    }

    private boolean lastUpdateTimeIsLessThanTTL(Show show) {
        LocalDateTime lastUpdate = show.getLastUpdate();
        final int ttl = 1;
        if (Duration.between(lastUpdate, LocalDateTime.now()).toHours() < ttl) {
            return true;
        } else {
            return false;
        }
    }

    private Show getTvMazeShow(int showId) {
        try {
            Show show = tvMazeService.getShowById(showId);
            setSchedule(show);
            setGenres(show);
            setLastUpdate(show);
            return show;
        } catch (TvMazeShowNotFoundException ex) {
            throw new ShowNotFoundException("Show id=" + showId + " Not Found");
        }
    }

    private void setLastUpdate(Show show) {
        show.setLastUpdate(LocalDateTime.now().withNano(0));
    }

    private void setGenres(Show show) {
        List<Genre> savedGenres = genreService.saveIfAbsent(show.getGenres());
        show.setGenres(savedGenres);
    }

    private void setSchedule(Show show) {
        Schedule scheduleWithSavedDays = scheduleService.getScheduleWithSavedDays(show.getSchedule());
        show.setSchedule(scheduleWithSavedDays);
    }

    @Override
    public Season getSeasonById(int showId, int seasonId) {
        if (showId < 1) {
            throw new ShowNotFoundException("Show id should be greater than or equal to 1");
        }
        if (seasonId < 1) {
            throw new SeasonNotFoundException("Season id should be greater than or equal to 1");
        }
        Show show = getShowById(showId);
        Season season = getSeason(seasonId, show).orElseGet(() -> getSeasonFromTvMaze(seasonId, show));

        return season;
    }


    private Optional<Season> getSeason(int seasonId, Show show) {
        Optional<Season> season;
        List<Season> seasons = show.getSeasons();
        if (seasons == null) return Optional.empty();
        if (seasonId <= seasons.size()) {
            season = Optional.of(seasons.get(seasonId - 1));
        } else {
            season = Optional.empty();
        }
        return season;
    }

    private Season getSeasonFromTvMaze(int seasonId, Show show) {
        Show savedShow = updateShow(show);
        return getSeason(seasonId, savedShow)
                .orElseThrow(() -> new SeasonNotFoundException("Show id=" + show.getId() + "Season id=" + seasonId + " Not found"));
    }

    private Show updateShow(Show show) {
        try {
            List<Season> seasons = tvMazeService.getSeasons(show.getId());

            for (Season season : seasons) {
                List<Episode> episodes = tvMazeService.getEpisodes(season.getId());
                season.setEpisodes(episodes);
            }
            show.setSeasons(seasons);
            return showRepository.save(show);
        } catch (TvMazeSeasonNotFoundException ex) {
            throw new SeasonNotFoundException("Seasons of Show id=" + show.getId() + "Not found");
        }
    }

    @Override
    public Episode getEpisode(int showId, int seasonNumber, int episodeNumber) {
        Season season = getSeasonById(showId, seasonNumber);
        List<Episode> episodes = season.getEpisodes();
        if (episodeNumber <= episodes.size()) {
            return episodes.get(episodeNumber - 1);
        } else {
            throw new EpisodeNotFoundException("Season number=" + seasonNumber
                    + " Episode number=" + episodeNumber + " Not Found");
        }
    }

    @Override
    public Episode getLastEpisode(int showId) {
        Show show = getShowById(showId);
        if (!lastUpdateTimeIsLessThanTTL(show)) {
            show = updateShow(show);
        }
        Season lastSeason = show.getSeasons().get(show.getSeasons().size() - 1);
        return lastSeason.getEpisodes().stream()
                .max(comparing(Episode::getNumber))
                .orElseThrow(EpisodeNotFoundException::new);
    }

}
 