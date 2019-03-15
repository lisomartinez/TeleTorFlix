package com.teletorflix.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TvMazeSeason {

    private final static Pattern pattern = Pattern.compile("(<.+?>)");

    private int id;
    private int number;
    private String name = "N/A";

    private int episodeOrder;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate premiereDate = LocalDate.of(1900, 1, 1);

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate = LocalDate.of(1900, 1, 1);

    private String imageUrl = "N/A";
    private String url = "N/A";
    private String summary = "N/A";

    public static TvMazeSeasonBuilder builder() {
        return new TvMazeSeasonBuilder();
    }

    @JsonSetter(value = "summary")
    private void setSummaryJson(String summaryJson) {
        if (summaryJson == null || summaryJson.isBlank()) {
            this.summary = "N/A";
        } else {
            Matcher matcher = pattern.matcher(summaryJson);
            this.summary = matcher.replaceAll("");
        }
    }

    @JsonProperty("image")
    private void originalImage(Map<String, String> image) {
        this.imageUrl = image.getOrDefault("original", "N/A");
    }

    public static class TvMazeSeasonBuilder {
        private int id;
        private int number;
        private String name;
        private int episodeOrder;
        private LocalDate premiereDate;
        private LocalDate endDate;
        private String imageUrl;
        private String url;
        private String summary;

        public TvMazeSeasonBuilder id(int id) {
            this.id = id;
            return this;
        }

        public TvMazeSeasonBuilder number(int number) {
            this.number = number;
            return this;
        }

        public TvMazeSeasonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TvMazeSeasonBuilder episodes(int episodes) {
            this.episodeOrder = episodes;
            return this;
        }

        public TvMazeSeasonBuilder premiereDate(LocalDate premiereDate) {
            this.premiereDate = premiereDate;
            return this;
        }

        public TvMazeSeasonBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public TvMazeSeasonBuilder image(String image) {
            this.imageUrl = image;
            return this;
        }

        public TvMazeSeasonBuilder url(String url) {
            this.url = url;
            return this;
        }

        public TvMazeSeasonBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public TvMazeSeason build() {
            return new TvMazeSeason(id, number, name, episodeOrder, premiereDate, endDate, imageUrl, url, summary);
        }
    }
}
