package com.teletorflix.app.repository;

import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {


    //@Query("SELECT p FROM Product p JOIN p.prices pr WHERE pr.type = 'euro'")
    @Query("SELECT season FROM Show show JOIN show.seasons season WHERE show.id = :showId AND season.number = :seasonId")
    Season findSeasonById(int showId, int seasonId);

    @Query("SELECT show.lastUpdate FROM Show show WHERE show.id = :showId")
    Optional<LocalDateTime> getShowLastUpdate(int showId);
}
