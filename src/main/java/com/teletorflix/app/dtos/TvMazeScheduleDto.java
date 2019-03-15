package com.teletorflix.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.teletorflix.app.model.ScheduleDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"time", "days"})
public class TvMazeScheduleDto {

    private Set<ScheduleDay> days;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public static TvMazeScheduleDto of(Set<ScheduleDay> days, LocalTime time) {
        return new TvMazeScheduleDto(days, time);
    }

    @JsonGetter(value = "days")
    private List<String> getDaysJson() {
        return days.stream()
                .map(ScheduleDay::getDay)
                .collect(Collectors.toList());
    }

    @JsonSetter(value = "days")
    private void setDaysJson(List<String> daysStr) {
        days = daysStr.stream()
                .map(ScheduleDay::of)
                .collect(Collectors.toSet());
    }
}
