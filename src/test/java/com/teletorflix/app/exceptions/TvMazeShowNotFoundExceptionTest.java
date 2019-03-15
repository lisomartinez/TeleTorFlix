package com.teletorflix.app.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TvMazeShowNotFoundExceptionTest {
    @Test
    @DisplayName("Create TvMazeShowNotFoundException without description returns instance with default message")
    void createTvMazeShowNotFoundException__WithoutDescription_returnInstanceWithDefaultMessage() {
        TvMazeShowNotFoundException ex = new TvMazeShowNotFoundException();
        assertThat(ex.getMessage()).isEqualTo("TvMaze Show Not Found Exception");
    }

    @Test
    @DisplayName("Create TvMazeShowNotFoundException error with description returns instance with default message and desciption")
    void createTvMazeShowNotFoundException__WithDescription_returnInstanceWithDefaultMessagePlusDescription() {
        TvMazeShowNotFoundException ex = new TvMazeShowNotFoundException("Error Description");
        assertThat(ex.getMessage()).isEqualTo("TvMaze Show Not Found Exception. Error Description");
    }
}