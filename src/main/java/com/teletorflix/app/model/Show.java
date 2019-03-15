package com.teletorflix.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;


/**
 * The {@code Show} class represents a TV Show.
 * <p>
 *
 * @author Lisandro Alejo Martinez
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "show", schema = "PUBLIC")
public class Show {

    @Id
    @Column(name = "show_id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "language", nullable = false)
    private String language;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    @JoinTable(name = "show_genre", schema = "PUBLIC",
            joinColumns = @JoinColumn(name = "show_id"),
            foreignKey = @ForeignKey(name = "fk_show_genre_genre"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"),
            inverseForeignKey = @ForeignKey(name = "fk_show_genre_show")
    )
    private List<Genre> genres;


    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "runtime", nullable = false)
    private int runtime;

    @Column(name = "premiered", nullable = false)
    private LocalDate premiered;

    @Column(name = "offical_site_url", nullable = false)
    private String officialSite;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id", foreignKey = @ForeignKey(name = "fk_show_schedule_id"),
            nullable = false,
            unique = true)
    private Schedule schedule;

    @Column(name = "imdb_url", nullable = false)
    private String imdb;

    @Column(name = "tv_maze_url", nullable = false)
    private String tvMaze;

    @Column(name = "image_url", nullable = false)
    private String image;

    @Column(name = "summary", nullable = false, length = 5000)
    private String summary;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "show_id", foreignKey = @ForeignKey(name = "fk_season_show"))
    private List<Season> seasons;


    /**
     * @return ShowBuilder this returns ShowBuilder.
     */
    public static ShowBuilder builder() {
        return new ShowBuilder();
    }

    /**
     * The {@code ShowBuilder} class implements the builder pattern for constructing a Show object.
     */
    public static class ShowBuilder {
        private int id;
        private String name;
        private String type;
        private String language;
        private List<Genre> genres;
        private String status;
        private int runtime;
        private LocalDate premiered;
        private String officialSite;
        private Schedule schedule;
        private String imdb;
        private String tvMaze;
        private String image;
        private String summary;
        private LocalDateTime lastUpdate;
        private List<Season> seasons;

        public ShowBuilder tvMaze(String tvMaze) {
            this.tvMaze = tvMaze;
            return this;
        }

        public ShowBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ShowBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShowBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ShowBuilder language(String language) {
            this.language = language;
            return this;
        }

        public ShowBuilder genres(List<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public ShowBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ShowBuilder runtime(int runtime) {
            if (runtime <= 0) {
                throw new IllegalArgumentException("Runtime should be greater than or equal to 1");
            }
            this.runtime = runtime;
            return this;
        }

        public ShowBuilder premiered(LocalDate premiered) {
            this.premiered = premiered;
            return this;
        }

        public ShowBuilder officialSite(String officialSite) {
            this.officialSite = officialSite;
            return this;
        }

        public ShowBuilder schedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public ShowBuilder imdb(String imdb) {
            this.imdb = imdb;
            return this;
        }

        public ShowBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ShowBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public ShowBuilder lastUpdate(LocalDateTime time) {
            this.lastUpdate = time;
            return this;
        }

        public ShowBuilder seasons(List<Season> seasons) {
            this.seasons = seasons;
            return this;
        }

        public Show build() {
            return new Show(id, name, type, language, genres, status, runtime, premiered, officialSite,
                    schedule, imdb, tvMaze, image, summary, lastUpdate, seasons);
        }
    }
}
