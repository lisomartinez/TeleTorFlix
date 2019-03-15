package com.teletorflix.app.controllers;

import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;
import com.teletorflix.app.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ShowController.SHOWS)
public class ShowController {

    public static final String SHOWS = "/shows";
    public static final String SHOW_ID = "/{id}";
    public static final String SEASONS = "/season";
    public static final String SEASON_NUMBER = "/{seasonNumber}";

    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping(SHOW_ID)
    @ResponseStatus(HttpStatus.OK)
    public Show getShow(@PathVariable int id) {
        return showService.getShowById(id);
    }

    @GetMapping(SHOW_ID + SEASONS + SEASON_NUMBER)
    @ResponseStatus(HttpStatus.OK)
    public Season getSeason(@PathVariable int id, @PathVariable int seasonNumber) {
        return showService.getSeasonById(id, seasonNumber);
    }

}
