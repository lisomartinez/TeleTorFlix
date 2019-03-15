package com.teletorflix.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DAYS_OF_WEEK", schema = "PUBLIC")
public class ScheduleDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAY_OF_WEEK_ID")
    private int id;

    @Column(name = "DAY", nullable = false, unique = true)
    private String day;

    public ScheduleDay(String day) {
        this.day = day;
    }

    public static ScheduleDay of(String day) {
        return new ScheduleDay(day);
    }
}
