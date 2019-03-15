package com.teletorflix.app.repository;

import com.teletorflix.app.model.Schedule;
import com.teletorflix.app.model.ScheduleDay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class ScheduleRepositoryIT {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void save_ValidInstance_ReturnsSavedInstance() {
        ScheduleDay scheduleDay = ScheduleDay.of("Monday");
        Schedule schedule = Schedule.of(Set.of(scheduleDay), LocalTime.of(22, 0));
        assertThat(schedule.getDays()).isEqualTo(Set.of(scheduleDay));
        assertThat(schedule.getTime()).isEqualTo(LocalTime.of(22, 0));
    }
}