package com.teletorflix.app.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TvMazeClientErrorExceptionTest {

    @Test
    @DisplayName("Create client error without description returns instance with default message")
    void createTvMazeClientError__WithoutDescription_returnInstanceWithDefaultMessage() {
        TvMazeClientErrorException ex = new TvMazeClientErrorException();
        assertThat(ex.getMessage()).isEqualTo("TV Maze API Exception");
    }

    @Test
    @DisplayName("Create client error with description returns instance with default message and desciption")
    void createTvMazeClientError__WithDescription_returnInstanceWithDefaultMessagePlusDescription() {
        TvMazeClientErrorException ex = new TvMazeClientErrorException("Error Description");
        assertThat(ex.getMessage()).isEqualTo("TV Maze API Exception. Error Description");
    }
}