package com.teletorflix.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TvMazeRestURLConstructorTest {

    private TvMazeURLConstructor urlConstructor;

    @BeforeEach
    void setUp() {
        urlConstructor = new TvMazeRestURLConstructor();
    }

    @Test
    @DisplayName("getBaseUrl returns http://api.tvmaze.com/")
    void getBaseURL() {
        assertThat(urlConstructor.getBaseURL()).isEqualTo("http://api.tvmaze.com/");
    }


    @Test
    @DisplayName("getShowByIdUrl wuth valid id return URL with id as path variable")
    void getShowByIdURL() {
        assertThat(urlConstructor.getShowByIdURL(1)).isEqualTo("http://api.tvmaze.com/shows/1");
    }

    @Test
    @DisplayName("getShowByIdUrl with id equals to 0  throws IllegalArgumentException")
    void getShowByIdURL_IdEqualsTo0_throwsNullPointerException() {
        int id = 0;
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getShowByIdURL(id));
    }

    @Test
    @DisplayName("getShowByIdUrl with id less than 0  throws IllegalArgumentException")
    void getShowByIdURL_IdLessThan0_throwsNullPointerException() {
        int id = -1;
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getShowByIdURL(id));
    }


    @Test
    @DisplayName("getShowsPageURL with valid page id return URL with id as parameter")
    void getShowsPageURL() {
        int page = 0;
        assertThat(urlConstructor.getShowsPageURL(page)).isEqualTo("http://api.tvmaze.com/shows?page=0");
    }

    @Test
    @DisplayName("getShowPageURL with id less than 0 throws IllegalArgumentException")
    void getShowPageURl_pageLessThan0_ThrowsIllegalArgumentException() {
        int page = -1;
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getShowsPageURL(page));
    }

    @Test
    @DisplayName("getSearchShowByName with partial show name returns URL with it as parameter")
    void getSearchShowByName() {
        assertThat(urlConstructor.getSearchShowByName("Greys")).isEqualTo("http://api.tvmaze.com/search/shows?q=Greys");
    }

    @Test
    @DisplayName("getSearchShowByName with null throws null pointer exception")
    void getSearchShowByName_withNull_throwsNullPointerException() {
        String showName = null;
        assertThrows(NullPointerException.class, () -> urlConstructor.getSearchShowByName(showName));
    }


    @Test
    @DisplayName("getSearchShowByName with empty string throws IllegalArgumentException")
    void getSearchShowByName_withEmptyShowName_throwsIllegalArgumentException() {
        String showName = "";
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getSearchShowByName(showName));
    }


    @Test
    @DisplayName("getSearchShowByName with blank string throws IllegalArgumentException")
    void getSearchShowByName_withBlankyShowName_throwsIllegalArgumentException() {
        String showName = "  ";
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getSearchShowByName(showName));
    }

    @Test
    @DisplayName("getSingleSearchByName with show name return URL with show name as parameter")
    void getSingleSearchByName() {
        assertThat(urlConstructor.getSingleSearchByName("Grey\'s Anatomy")).isEqualTo("http://api.tvmaze.com/singlesearch/shows?q=Grey\'s Anatomy");
    }


    @Test
    @DisplayName("getSingleSearchShowByName with empty string throws IllegalArgumentException")
    void getSingleSearch_withNull_ThrowsNullPointerException() {
        String showName = null;
        assertThrows(NullPointerException.class, () -> urlConstructor.getSingleSearchByName(showName));
    }

    @Test
    @DisplayName("getSingleSearchShowByName with blank string throws IllegalArgumentException")
    void getSingleSearch_withBlank_ThrowsNullPointerException() {
        String showName = "    ";
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getSingleSearchByName(showName));
    }

    @Test
    @DisplayName("getSeasonURL should return Show Seasons URL")
    void getSeasonsURL_validShowId_ShouldReturnShowSeasonsURL() {
        final int showId = 1;
        assertThat(urlConstructor.getShowSeasonsURL(showId)).isEqualTo("http://api.tvmaze.com/shows/1/seasons");
    }

    @Test
    @DisplayName("getSeasonURL should return Show Seasons URL")
    void getSeasonsURL_invalidShowId_ShouldReturnShowSeasonsURL() {
        final int showId = 0;
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getShowSeasonsURL(showId));
    }

    @Test
    @DisplayName("getEpisodeURL should return Season Episode URL")
    void getEpisodeURL_validSeasond_ShouldReturnEpisodeSeasonsURL() {
        final int seasonId = 1;
        assertThat(urlConstructor.getEpisodesURL(seasonId)).isEqualTo("http://api.tvmaze.com/seasons/1/episodes");
    }

    @Test
    @DisplayName("getEpisodeURL should throws IllegalArgumentExceptionL")
    void getSeasonsURL_invalidShowId_ShouldThrowIllegalArgumentException() {
        final int seasonId = 0;
        assertThrows(IllegalArgumentException.class, () -> urlConstructor.getEpisodesURL(seasonId));
    }

}