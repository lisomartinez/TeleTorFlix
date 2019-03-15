package com.teletorflix.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleDayTest {

    @Test
    @DisplayName("Static Factory should return instance")
    void staticFactoryMethod_ReturnsInstance() {
        ScheduleDay scheduleDay = ScheduleDay.of("Monday");
        assertThat(scheduleDay.getDay()).isEqualTo("Monday");
    }
}