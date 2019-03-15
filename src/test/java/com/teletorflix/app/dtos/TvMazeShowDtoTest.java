package com.teletorflix.app.dtos;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.model.ScheduleDay;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TvMazeShowDtoTest {

    private JacksonTester<TvMazeShowDto> json;
    private Logger logger;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    @DisplayName("Deserialize Show JSON should return Show instance")
        // Make an HTTP GET request to http://api.tvmaze.com/shows/1 and save the contents of your response in a file. The path is located in JsonTestFiles.getShow()
    void deserializeShowJson_returnShowInstance() throws IOException {
        TvMazeShowDto show = getShowDto();

        TvMazeShowDto tvMazeShowDto = getShowFromJson("default");

        assertThat(tvMazeShowDto).isEqualTo(show);
    }

    private TvMazeShowDto getShowFromJson(String option) throws IOException {
        if (option.equals("blank")) return json.readObject(JsonTestFiles.getShowWithSummaryBlank());
        return json.readObject(JsonTestFiles.getShow());
    }

    private TvMazeShowDto getShowDto() {
        return TvMazeShowDto.builder()
                .id(1)
                .name("Under the Dome")
                .type("Scripted")
                .language("English")
                .genres(List.of("Drama", "Science-Fiction", "Thriller"))
                .status("Ended")
                .runtime(60)
                .premiered(LocalDate.of(2013, 6, 24))
                .officialSite("http://www.cbs.com/shows/under-the-dome/")
                .schedule(TvMazeScheduleDto.of(Set.of(ScheduleDay.of("Thursday")), LocalTime.of(22, 0)))
                .tvMazeUrl("http://www.tvmaze.com/shows/1/under-the-dome")
                .imdbUrl("https://www.imdb.com/title/tt1553656")
                .imageUrl("http://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg")
                .summary("Under the Dome is the story of a small town that is suddenly and inexplicably " +
                        "sealed off from the rest of the world by an enormous transparent dome. " +
                        "The town's inhabitants must deal with surviving the post-apocalyptic conditions " +
                        "while searching for answers about the dome, where it came from and if and when" +
                        " it will go away.")
                .build();
    }


    @Test
    void createShow_withSummaryNull_CreateSummaryFiledWithoutSummaryFieldSetToNA() throws IOException {
        TvMazeShowDto tvMazeShowDto = getShowFromJson("blank");
        assertThat(tvMazeShowDto.getSummary()).isEqualTo("N/A");

    }

    @Test
    @DisplayName("Serialize Show NULL should throw IllegalArgumentException")
    void serialize_Null_Throws() throws IOException {
        TvMazeShowDto show = null;
        assertThrows(IllegalArgumentException.class, () -> json.write(show));
    }
}
