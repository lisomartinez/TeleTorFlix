package com.teletorflix.app.service;

import com.teletorflix.app.model.Episode;
import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;

public interface ShowService {
    Show getShowById(int id);

    Season getSeasonById(int showId, int seasonId);

    Episode getEpisode(int showId, int seasonNumber, int episodeNumber);

    Episode getLastEpisode(int showId);
}
