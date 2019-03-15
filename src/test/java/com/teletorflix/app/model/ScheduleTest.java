package com.teletorflix.app.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleTest {

    @Test
    void staticFactoryMethod_returnsInstance() {
        ScheduleDay scheduleDay = ScheduleDay.of("Monday");
        Schedule schedule = Schedule.of(Set.of(scheduleDay), LocalTime.of(22, 0));
        assertThat(schedule.getDays()).containsExactly(scheduleDay);
        assertThat(schedule.getTime()).isEqualTo(LocalTime.of(22, 0));
    }
}