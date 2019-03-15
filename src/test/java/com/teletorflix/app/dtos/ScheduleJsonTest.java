package com.teletorflix.app.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.model.ScheduleDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleJsonTest {

    private JacksonTester<TvMazeScheduleDto> json;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    @DisplayName("Deserialize Schedule JSON with ONE day should return Schedule instance")
    void deserialize_ScheduleJSONWithOneDay_ReturnsSchedule() throws IOException {
        TvMazeScheduleDto schedule = getScheduleWithOneDay();
        String scheduleJson = getScheduleJsonWithOneDay();

        ObjectContent<TvMazeScheduleDto> parsed = json.parse(scheduleJson);

        assertThat(parsed).isEqualTo(schedule);
    }

    private TvMazeScheduleDto getScheduleWithOneDay() {
        return TvMazeScheduleDto.of(Set.of(ScheduleDay.of("Thursday")), LocalTime.of(22, 0));
    }

    private String getScheduleJsonWithOneDay() {
        return "{ \"time\": \"22:00\", \"days\": [ \"Thursday\" ] }";
    }

    @Test
    @DisplayName("Deserialize Schedule JSON with TWO days should return Schedule instance")
    void deserialize_ScheduleJSONWithTwoDays_ReturnsSchedule() throws IOException {
        TvMazeScheduleDto schedule = getScheduleWithTwoDays();
        String scheduleJson = getScheduleJsonWithTwoDay();

        ObjectContent<TvMazeScheduleDto> parsed = json.parse(scheduleJson);

        assertThat(parsed).isEqualTo(schedule);
    }

    private TvMazeScheduleDto getScheduleWithTwoDays() {
        return new TvMazeScheduleDto(Set.of(ScheduleDay.of("Monday"), ScheduleDay.of("Thursday")), LocalTime.of(22, 0));
    }

    private String getScheduleJsonWithTwoDay() {
        return "{ \"time\": \"22:00\", \"days\": [ \"Monday\", \"Thursday\" ] }";
    }

    @Test
    @DisplayName("Serialize Schedule with ONE day should return JSON string")
    void serializeSchedule_ScheduleWithOneDay_ReturnJSONString() throws IOException {
        TvMazeScheduleDto schedule = getScheduleWithOneDay();
        String jsonExpected = getScheduleJsonWithOneDay();

        JsonContent<TvMazeScheduleDto> jsonContent = json.write(schedule);

        assertThat(jsonContent).isEqualToJson(jsonExpected);
    }

    @Test
    @DisplayName("Serialize Schedule with TWO days should return JSON String")
    void serializeSchedule_ScheduleWithTwoDay_ReturnJSONString() throws IOException {
        TvMazeScheduleDto schedule = getScheduleWithTwoDays();
        String jsonExpected = getScheduleJsonWithTwoDay();

        JsonContent<TvMazeScheduleDto> jsonContent = json.write(schedule);

        assertThat(jsonContent).isEqualToJson(jsonExpected);
    }
}
