package com.teletorflix.app.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TvMazeTooManyRequestsExceptionTest {
    @Test
    @DisplayName("Create TvMazeTooManyRequestsException without description returns instance with default message")
    void createTvMazeTooManyRequestsException__WithoutDescription_returnInstanceWithDefaultMessage() {
        TvMazeTooManyRequestsException ex = new TvMazeTooManyRequestsException();
        assertThat(ex.getMessage()).isEqualTo("TV Maze Too Many Request");
    }

    @Test
    @DisplayName("Create TvMazeTooManyRequestsException error with description returns instance with default message and desciption")
    void createTvMazeTooManyRequestsException__WithDescription_returnInstanceWithDefaultMessagePlusDescription() {
        TvMazeTooManyRequestsException ex = new TvMazeTooManyRequestsException("Error Description");
        assertThat(ex.getMessage()).isEqualTo("TV Maze Too Many Request. Error Description");
    }
}