package com.teletorflix.app.repository;

import com.teletorflix.app.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Integer> {
    Optional<ScheduleDay> findByDay(String day);
}
