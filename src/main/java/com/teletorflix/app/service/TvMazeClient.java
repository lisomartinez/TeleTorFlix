package com.teletorflix.app.service;

import com.teletorflix.app.dtos.TvMazeEpisodeDto;
import com.teletorflix.app.dtos.TvMazeSeason;
import com.teletorflix.app.dtos.TvMazeShowDto;

import java.util.List;

public interface TvMazeClient {
    List<TvMazeShowDto> findPage(int page);
    List<TvMazeSeason> getShowSeasons(int showId);
    TvMazeShowDto getShowById(int id);

    List<TvMazeEpisodeDto> getEpisodes(int seasonId);
}
