package com.teletorflix.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.teletorflix.app.model.Genre;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TvMazeShowDto {

    private final static Pattern pattern = Pattern.compile("(<.+?>)");

    private static final String DEFAULT = "N/A";

    private int id;
    private String name;
    private String type;
    private String language;
    private List<Genre> genres;
    private String status;
    private int runtime = 0;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate premiered = LocalDate.of(1900, 1, 1);

    private String officialSite;
    private TvMazeScheduleDto schedule;
    private String tvMazeUrl;
    private String imdbUrl;
    private String imageUrl;
    private String summary;

    private LocalDateTime updateDateTime;

    public static ShowDtoBuilder builder() {
        return new ShowDtoBuilder();
    }

    @JsonProperty("type")
    private void setTypeOrDefault(String type) {
        this.type = getOrDefault(type);
    }

    private String getOrDefault(String field) {
        return field == null ? "N/A" : field;
    }

    @JsonProperty("language")
    private void setLanguageOrDefault(String language) {
        this.language = getOrDefault(language);
    }

    @JsonProperty("status")
    private void setStatusOrDefault(String status) {
        this.status = getOrDefault(status);
    }

    @JsonSetter("genres")
    private void setGenresList(List<String> genres) {
        if (genres.isEmpty()) {
            this.genres = List.of(Genre.of("N/A"));
        } else {
            this.genres = genres.stream().map(Genre::of).collect(Collectors.toList());
        }
    }

    @JsonProperty("officialSite")
    private void setOfficialSiteOrDefault(String officialSite) {
        this.officialSite = getOrDefault(officialSite);
    }

    @JsonProperty("url")
    private void setTvMazeUrl(String url) {
        this.tvMazeUrl = getOrDefault(url);
    }

    @JsonProperty(value = "image")
    private void originalImage(Map<String, String> image) {

        if (image == null || image.isEmpty()) {
            this.imageUrl = "N/A";
        } else {
            this.imageUrl = image.putIfAbsent("original", "N/A");
        }
    }

    @JsonProperty("externals")
    private void imdbUrl(Map<String, String> externals) {
        String imdb = externals.getOrDefault("imdb", "N/A");
        if (!imdb.equals("N/A")) {
            this.imdbUrl = "https://www.imdb.com/title/" + imdb;
        } else {
            this.imdbUrl = imdb;
        }
    }


    @JsonProperty(value = "summary")
    private void setSummaryJson(String summaryJson) {
        if (summaryJson == null || summaryJson.isBlank()) {
            this.summary = "N/A";
        } else {
            Matcher matcher = pattern.matcher(summaryJson);
            this.summary = matcher.replaceAll("");
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genres == null) ? 0 : genres.hashCode());
        result = prime * result + id;
        result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        result = prime * result + ((imdbUrl == null) ? 0 : imdbUrl.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((officialSite == null) ? 0 : officialSite.hashCode());
        result = prime * result + ((premiered == null) ? 0 : premiered.hashCode());
        result = prime * result + runtime;
        result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + ((tvMazeUrl == null) ? 0 : tvMazeUrl.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TvMazeShowDto other = (TvMazeShowDto) obj;
        if (genres == null) {
            if (other.genres != null)
                return false;
        } else if (!genres.equals(other.genres))
            return false;
        if (id != other.id)
            return false;
        if (imageUrl == null) {
            if (other.imageUrl != null)
                return false;
        } else if (!imageUrl.equals(other.imageUrl))
            return false;
        if (imdbUrl == null) {
            if (other.imdbUrl != null)
                return false;
        } else if (!imdbUrl.equals(other.imdbUrl))
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (officialSite == null) {
            if (other.officialSite != null)
                return false;
        } else if (!officialSite.equals(other.officialSite))
            return false;
        if (premiered == null) {
            if (other.premiered != null)
                return false;
        } else if (!premiered.equals(other.premiered))
            return false;
        if (runtime != other.runtime)
            return false;
        if (schedule == null) {
            if (other.schedule != null)
                return false;
        } else if (!schedule.equals(other.schedule))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (summary == null) {
            if (other.summary != null)
                return false;
        } else if (!summary.equals(other.summary))
            return false;
        if (tvMazeUrl == null) {
            if (other.tvMazeUrl != null)
                return false;
        } else if (!tvMazeUrl.equals(other.tvMazeUrl))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }


    public static class ShowDtoBuilder {
        private int id;
        private String name;
        private String type = "N/A";
        private String language = "N/A";
        private List<Genre> genres = List.of(Genre.of("N/A"));
        private String status = "N/A";
        private int runtime = 0;

        private LocalDate premiered = LocalDate.of(1900, 1, 1);

        private String officialSite = "N/A";
        private TvMazeScheduleDto schedule = new TvMazeScheduleDto();

        private String tvMazeUrl = "N/A";
        private String imdbUrl = "N/A";
        private String imageUrl = "N/A";
        private String summary = "N/A";


        public ShowDtoBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ShowDtoBuilder tvMazeUrl(String url) {
            this.tvMazeUrl = url;
            return this;
        }

        public ShowDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShowDtoBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ShowDtoBuilder language(String language) {
            this.language = language;
            return this;
        }

        public ShowDtoBuilder genres(List<String> genres) {
            this.genres = genres.stream().map(Genre::of).collect(Collectors.toList());
            return this;
        }

        public ShowDtoBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ShowDtoBuilder runtime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        public ShowDtoBuilder premiered(LocalDate premiered) {
            this.premiered = premiered;
            return this;
        }

        public ShowDtoBuilder officialSite(String officialSite) {
            this.officialSite = officialSite;
            return this;
        }

        public ShowDtoBuilder schedule(TvMazeScheduleDto schedule) {
            this.schedule = schedule;
            return this;
        }

        public ShowDtoBuilder imageUrl(String image) {
            this.imageUrl = image;
            return this;
        }

        public ShowDtoBuilder imdbUrl(String url) {
            this.imdbUrl = url;
            return this;
        }

        public ShowDtoBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public TvMazeShowDto build() {
            LocalDateTime updateTime = LocalDateTime.now();
            return new TvMazeShowDto(id, name, type, language, genres, status, runtime, premiered, officialSite,
                    schedule, tvMazeUrl, imdbUrl, imageUrl, summary, updateTime);
        }
    }

}
