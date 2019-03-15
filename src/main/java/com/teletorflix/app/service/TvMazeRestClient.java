package com.teletorflix.app.service;

import com.teletorflix.app.dtos.TvMazeEpisode;
import com.teletorflix.app.dtos.TvMazeSeason;
import com.teletorflix.app.dtos.TvMazeShowDto;
import com.teletorflix.app.exceptions.TvMazeSeasonInvalidException;
import com.teletorflix.app.exceptions.TvMazeShowInvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class TvMazeRestClient implements TvMazeClient {

    private RestTemplate restTemplate;
    private TvMazeURLConstructor tvMazeURLConstructor;

    @Autowired
    public TvMazeRestClient(RestTemplateBuilder restTemplateBuilder,
                            TvMazeURLConstructor URLConstructor,
                            TvMazeRestRespondeErrorHandler errorHandler) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(errorHandler)
                .build();
        this.tvMazeURLConstructor = URLConstructor;
    }

    @Override
    public TvMazeShowDto getShowById(int id) {
        try {
            String url = tvMazeURLConstructor.getShowByIdURL(id);
            return restTemplate.getForObject(url, TvMazeShowDto.class);
        } catch (IllegalArgumentException ex) {
            throw new TvMazeShowInvalidIdException("Show id invalid " + id);
        }
    }

    @Override
    public List<TvMazeShowDto> findPage(int page) {
        try {
            String url = tvMazeURLConstructor.getShowsPageURL(page);
            ResponseEntity<List<TvMazeShowDto>> response = restTemplate.exchange(
                    url,
                    GET,
                    null,
                    new ParameterizedTypeReference<List<TvMazeShowDto>>() {
                    });
            return response.getBody();
        } catch (IllegalArgumentException ex) {
            throw new TvMazeShowInvalidIdException("Show page id invalid " + page);
        }
    }

    @Override
    public List<TvMazeSeason> getShowSeasons(int showId) {
        try {
            String url = tvMazeURLConstructor.getShowSeasonsURL(showId);
            ResponseEntity<List<TvMazeSeason>> response = restTemplate.exchange(
                    url,
                    GET,
                    null,
                    new ParameterizedTypeReference<List<TvMazeSeason>>() {
                    });
            return response.getBody();
        } catch (IllegalArgumentException ex) {
            throw new TvMazeShowInvalidIdException("Show id invalid " + showId);
        }
    }

    @Override
    public List<TvMazeEpisode> getEpisodes(int seasonId) {
        try {
            String url = tvMazeURLConstructor.getEpisodesURL(seasonId);
            ResponseEntity<List<TvMazeEpisode>> response = restTemplate.exchange(
                    url,
                    GET,
                    null,
                    new ParameterizedTypeReference<List<TvMazeEpisode>>() {
                    });
            return response.getBody();
        } catch (IllegalArgumentException ex) {
            throw new TvMazeSeasonInvalidException("Season id=" + "inavlid");
        }
    }
}
