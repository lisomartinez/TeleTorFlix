package com.teletorflix.app.service;

import com.teletorflix.app.model.Genre;
import com.teletorflix.app.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> saveIfAbsent(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .map(this::saveIfAbasent)
                .collect(Collectors.toList());
    }

    private Genre saveIfAbasent(String name) {
        if (name == null) throw new RuntimeException();
        return genreRepository.findByName(name)
                .orElseGet(() -> genreRepository.save(Genre.of(name)));
    }

}
