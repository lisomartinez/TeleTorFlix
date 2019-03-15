package com.teletorflix.app.service;


import com.teletorflix.app.model.Episode;
import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;

import java.util.List;

public interface TvMazeService {

    Show getShowById(int id);

    List<Season> getSeasons(int showId);

    List<Episode> getEpisodes(int seasonId);
}
