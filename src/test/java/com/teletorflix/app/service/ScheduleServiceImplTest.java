package com.teletorflix.app.service;

import com.teletorflix.app.exceptions.InvalidScheduleDayException;
import com.teletorflix.app.model.Schedule;
import com.teletorflix.app.model.ScheduleDay;
import com.teletorflix.app.repository.ScheduleDayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleServiceImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ScheduleDayRepository scheduleDayRepository;

    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() throws Exception {
        scheduleService = new ScheduleServiceImpl(scheduleDayRepository);
    }

    @Test
    void getScheduleWithSavedDays_ScheduleWithValidScheduleDay_ShouldReturnScheduleWithScheduleDayFetchedFromDB() {
        Schedule schedule = Schedule.of(Set.of(ScheduleDay.of("Tuesday")), LocalTime.of(22, 00));
        Schedule expected = Schedule.of(Set.of(testEntityManager.find(ScheduleDay.class, 2)), LocalTime.of(22, 00));
        Schedule result = scheduleService.getScheduleWithSavedDays(schedule);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getScheduleWithSaveDays_ScheduleWithInvalidDay_ShouldThrowInvalidScheduleDayException() {
        Schedule schedule = Schedule.of(Set.of(ScheduleDay.of("Invalid")), LocalTime.of(22, 00));
        assertThrows(InvalidScheduleDayException.class, () -> scheduleService.getScheduleWithSavedDays(schedule));
    }
}
