package com.teletorflix.app.service;

import com.teletorflix.app.model.Genre;
import com.teletorflix.app.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GenreServiceImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreRepository genreRepository;

    private GenreService genreService;

    @BeforeEach
    void setUp() throws Exception {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    void saveIfAbsent_GenresNotInDB_ShouldSaveAndInstance() {
        List<Genre> genres = List.of(Genre.of("Drama"), Genre.of("Adventure"), Genre.of("Science-Fiction"));
        List<Genre> expected = List.of(new Genre(1, "Drama"), new Genre(2, "Adventure"), new Genre(3, "Science-Fiction"));
        List<Genre> saved = genreService.saveIfAbsent(genres);

        assertThat(saved).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void saveIfAbsent_GenresInDB_ShouldReturnInsntace() {
        testEntityManager.persistAndFlush(Genre.of("Drama"));
        testEntityManager.persistAndFlush(Genre.of("Adventure"));
        testEntityManager.persistAndFlush(Genre.of("Science-Fiction"));

        Genre drama = genreRepository.findByName("Drama").get();
        Genre adventure = genreRepository.findByName("Adventure").get();
        Genre scienceFiction = genreRepository.findByName("Science-Fiction").get();

        List<Genre> genres = List.of(Genre.of("Drama"), Genre.of("Adventure"), Genre.of("Science-Fiction"));
        List<Genre> expected = List.of(drama, adventure, scienceFiction);
        List<Genre> saved = genreService.saveIfAbsent(genres);

        assertThat(saved).containsExactlyInAnyOrderElementsOf(expected);
    }

}
