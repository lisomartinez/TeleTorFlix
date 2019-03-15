package com.teletorflix.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule", schema = "PUBLIC")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int id;

    @ManyToMany
    @JoinTable(name = "schedule_days_of_week", schema = "PUBLIC",
            joinColumns = @JoinColumn(name = "schedule_id"),
            foreignKey = @ForeignKey(name = "fk_schedule_days_of_week_schedule"),
            inverseJoinColumns = @JoinColumn(name = "days_of_week_id"),
            inverseForeignKey = @ForeignKey(name = "fk_schedule_days_of_week_days_of_week")
    )
    private Set<ScheduleDay> days;

    @Column(name = "time_of_show")
    private LocalTime time;

    public Schedule(Set<ScheduleDay> days, LocalTime time) {
        this.days = days;
        this.time = time;
    }

    public static Schedule of(Set<ScheduleDay> days, LocalTime time) {
        return new Schedule(days, time);
    }
}
