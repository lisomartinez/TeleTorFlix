package com.teletorflix.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "episode", schema = "Public")
public class Episode {

    @Id
    @Column(name = "episode_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private int number;

    @Column(name = "airtdate")
    private LocalDate airdate;

    @Column(name = "airtime")
    private LocalTime airtime;

    @Column(name = "runtime")
    private int runtime;

    @Column(name = "image")
    private String image;

    @Column(name = "tvmaze_url")
    private String tvMaze;

    @Column(name = "summary")
    private String summary;

    public Episode(int id, String name, int number, LocalDate airdate,
                   LocalTime airtime, int runtime, String image,
                   String tvMaze, String summary) {

        this.id = id;
        this.name = name;
        this.number = number;
        this.airdate = airdate;
        this.airtime = airtime;
        this.runtime = runtime;
        this.image = image;
        this.tvMaze = tvMaze;
        this.summary = summary;
    }

    public static EpisodeBuilder builder() {
        return new EpisodeBuilder();
    }

    public static class EpisodeBuilder {

        private int id;
        private String name;
        private int number;
        private LocalDate airdate;
        private LocalTime airtime;
        private int runtime;
        private String image;
        private String tvMaze;
        private String summary;

        public EpisodeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EpisodeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EpisodeBuilder number(int number) {
            this.number = number;
            return this;
        }

        public EpisodeBuilder airDate(LocalDate airdate) {
            this.airdate = airdate;
            return this;
        }

        public EpisodeBuilder airTime(LocalTime airtime) {
            this.airtime = airtime;
            return this;
        }

        public EpisodeBuilder runtime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        public EpisodeBuilder image(String image) {
            this.image = image;
            return this;
        }

        public EpisodeBuilder tvMaze(String tvMaze) {
            this.tvMaze = tvMaze;
            return this;
        }

        public EpisodeBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Episode build() {
            return new Episode(id, name, number, airdate, airtime, runtime, image, tvMaze, summary);
        }
    }
}
