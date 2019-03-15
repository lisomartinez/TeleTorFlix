package com.teletorflix.app.repository;

import com.teletorflix.app.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@DirtiesContext
class GenreRepositoryIT {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Save Genre should return saved instance")
    void saveGenre_validGenre_ReturnsGenre() {
        Genre expected = Genre.of("Drama");
        Genre saved = genreRepository.save(expected);
        expected.setId(1);

        assertThat(saved).isEqualTo(expected);
    }

    @Test
    @DisplayName("save with duplicate name should return same name")
    void saveGenre_DuplicateGenre_ReturnsSameGenre() {
        Genre expected = Genre.of("Drama");
        Genre save = genreRepository.save(expected);
        Genre save1 = genreRepository.save(expected);
        assertThat(save).isEqualTo(save1);
        assertThat(genreRepository.count()).isEqualTo(1);
    }


    @Test
    @DisplayName("Save with updated Genre should return updated name")
    void saveGenre_UpdatedGenre_ReturnsUpdatedGenre() {
        Genre expected = Genre.of("Drama");
        Genre save = genreRepository.save(expected);
        assertThat(save.getName()).isEqualTo("Drama");

        save.setName("Adventure");
        Genre save1 = genreRepository.save(expected);

        assertThat(save.getName()).isEqualTo("Adventure");
        assertThat(save1.getName()).isEqualTo("Adventure");

        assertThat(genreRepository.count()).isEqualTo(1);
    }


}