package com.teletorflix.app.service;

import org.springframework.stereotype.Component;

@Component
public class TvMazeRestURLConstructor implements TvMazeURLConstructor {

    private static final String BASE_URL = "http://api.tvmaze.com/";
    private static final String SHOWS = "shows/";
    private static final String SHOWS_PAGE = "shows?page=";
    private static final String SEARCH = "search/shows?q=";
    private static final String SINGLE_SEARCH = "singlesearch/shows?q=";
    private static final String SEASONS = "seasons";
    private static final String EPISODES = "/episodes";

    private StringBuilder sb;

    public TvMazeRestURLConstructor() {
        this.sb = new StringBuilder();
    }

    @Override
    public String getBaseURL() {
        return BASE_URL;
    }

    @Override
    public String getShowByIdURL(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("id should be greater than 0");
        }
//        return BASE_URL + SHOWS + id;
        String url = sb.append(BASE_URL).append(SHOWS).append(id).toString();
        sb.setLength(0);
        return url;
    }

    @Override
    public String getShowsPageURL(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("page should be equals to or greater than 0");
        }
        String url = sb.append(BASE_URL).append(SHOWS_PAGE).append(page).toString();
        sb.setLength(0);
        return url;
    }

    @Override
    public String getSearchShowByName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty or blank");
        }
        String url = sb.append(BASE_URL).append(SEARCH).append(name).toString();
        sb.setLength(0);
        return url;
    }

    @Override
    public String getSingleSearchByName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty or blank");
        }
        String url = sb.append(BASE_URL).append(SINGLE_SEARCH).append(name).toString();
        sb.setLength(0);
        return url;
    }

    @Override
    public String getShowSeasonsURL(int showId) {
        if (showId < 1) {
            throw new IllegalArgumentException("id should be greater than 0");
        }
        String url = sb.append(BASE_URL).append(SHOWS).append(showId).append("/").append(SEASONS).toString();
        sb.setLength(0);
        return url;
    }

    @Override
    public String getEpisodesURL(int seasonId) {
        if (seasonId < 1) {
            throw new IllegalArgumentException("id should be greater than 0");
        }
        String url = sb.append(BASE_URL).append(SEASONS).append("/").append(seasonId).append(EPISODES).toString();
        sb.setLength(0);
        return url;
    }
}
