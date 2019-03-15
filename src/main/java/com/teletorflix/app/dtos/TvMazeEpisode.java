package com.teletorflix.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvMazeEpisode {

    private static final Pattern pattern = Pattern.compile("(<.+?>)");

    private int id;
    private String name = "N/A";
    private int number;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate airdate;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime airtime;
    private int runtime;
    private String image;

    @JsonProperty("url")
    private String tvMaze = "N/A";
    private String summary;

    public static TvMazeEpisodeBuilder builder() {
        return new TvMazeEpisodeBuilder();
    }

    @JsonProperty("image")
    private void originalImage(Map<String, String> imageUrl) {
        this.image = imageUrl.getOrDefault("original", "N/A");
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

    public static class TvMazeEpisodeBuilder {
        private int id;
        private String name;
        private int number;
        private LocalDate airdate;
        private LocalTime airtime;
        private int runtime;
        private String image;
        private String tvMaze;
        private String summary;

        public TvMazeEpisodeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public TvMazeEpisodeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TvMazeEpisodeBuilder number(int number) {
            this.number = number;
            return this;
        }

        public TvMazeEpisodeBuilder airdate(LocalDate airdate) {
            this.airdate = airdate;
            return this;
        }

        public TvMazeEpisodeBuilder airtime(LocalTime airtime) {
            this.airtime = airtime;
            return this;
        }

        public TvMazeEpisodeBuilder runtime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        public TvMazeEpisodeBuilder image(String image) {
            this.image = image;
            return this;
        }

        public TvMazeEpisodeBuilder tvMaze(String tvMaze) {
            this.tvMaze = tvMaze;
            return this;
        }

        public TvMazeEpisodeBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public TvMazeEpisode build() {
            return new TvMazeEpisode(id, name, number, airdate, airtime, runtime, image, tvMaze, summary);
        }
    }
}
