package com.teletorflix.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "season", schema = "PUBLIC")
public class Season {

    @Id
    @Column(name = "season_id")
    private int id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "episode_order", nullable = false)
    private int episodeOrder;

    @Column(name = "premiere_date", nullable = false)
    private LocalDate premiereDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "image_url", nullable = false)
    private String image;

    @Column(name = "tvmaze_url", nullable = false)
    private String tvMaze;

    @Column(name = "summary", nullable = false, length = 5000)
    private String summary;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "season_id", foreignKey = @ForeignKey(name = "fk_episode_season"))
    private List<Episode> episodes;

    public Season(int id, int number, int episodeOrder, LocalDate premiereDate,
                  LocalDate endDate, String image, String tvMaze, String summary, List<Episode> episodes) {
        this.id = id;
        this.number = number;
        this.episodeOrder = episodeOrder;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
        this.image = image;
        this.tvMaze = tvMaze;
        this.summary = summary;
        this.episodes = episodes;
    }

    public static SeasonBuilder builder() {
        return new SeasonBuilder();
    }

    public static class SeasonBuilder {

        private int id;
        private int number;
        private int episodeOrder;
        private LocalDate premiereDate;
        private LocalDate endDate;
        private String image;
        private String tvMaze;
        private String summary;
        private List<Episode> episodes;

        public SeasonBuilder id(int id) {
            this.id = id;
            return this;
        }

        public SeasonBuilder number(int number) {
            this.number = number;
            return this;
        }

        public SeasonBuilder episodeOrder(int episodeOrder) {
            this.episodeOrder = episodeOrder;
            return this;
        }

        public SeasonBuilder premiereDate(LocalDate premiereDate) {
            this.premiereDate = premiereDate;
            return this;
        }

        public SeasonBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public SeasonBuilder image(String imageUrl) {
            this.image = imageUrl;
            return this;
        }

        public SeasonBuilder tvMaze(String tvMazeUrl) {
            this.tvMaze = tvMazeUrl;
            return this;
        }

        public SeasonBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public SeasonBuilder episodes(List<Episode> episodes) {
            this.episodes = episodes;
            return this;
        }

        public Season build() {
            return new Season(id, number, episodeOrder, premiereDate, endDate, image, tvMaze, summary, episodes);
        }
    }
}
