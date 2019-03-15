package com.teletorflix.app.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShowNotFoundExceptionTest {
    @Test
    @DisplayName("Create ShowNotFoundException without description returns instance with default message")
    void createTvMazeClientError__WithoutDescription_returnInstanceWithDefaultMessage() {
        ShowNotFoundException ex = new ShowNotFoundException();
        assertThat(ex.getMessage()).isEqualTo("Show not found exception");
    }

    @Test
    @DisplayName("Create ShowNotFoundException with description returns instance with default message and desciption")
    void createTvMazeClientError__WithDescription_returnInstanceWithDefaultMessagePlusDescription() {
        ShowNotFoundException ex = new ShowNotFoundException("Error Description");
        assertThat(ex.getMessage()).isEqualTo("Show not found exception. Error Description");
    }
}