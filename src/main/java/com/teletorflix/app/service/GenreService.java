package com.teletorflix.app.service;

import com.teletorflix.app.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> saveIfAbsent(List<Genre> genres);
}
