package com.teletorflix.app.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TvMazeServerErrorExceptionTest {
    @Test
    @DisplayName("Create TvMazeServerErrorException without description returns instance with default message")
    void createTTvMazeServerErrorException__WithoutDescription_returnInstanceWithDefaultMessage() {
        TvMazeServerErrorException ex = new TvMazeServerErrorException();
        assertThat(ex.getMessage()).isEqualTo("TvMazeServerErrorException");
    }

    @Test
    @DisplayName("Create TvMazeServerErrorException error with description returns instance with default message and desciption")
    void createTvMazeServerErrorException__WithDescription_returnInstanceWithDefaultMessagePlusDescription() {
        TvMazeServerErrorException ex = new TvMazeServerErrorException("Error Description");
        assertThat(ex.getMessage()).isEqualTo("TvMazeServerErrorException. Error Description");
    }
}