package com.teletorflix.app.service;

import com.teletorflix.app.exceptions.TvMazeClientErrorException;
import com.teletorflix.app.exceptions.TvMazeResourceNotFoundException;
import com.teletorflix.app.exceptions.TvMazeServerErrorException;
import com.teletorflix.app.exceptions.TvMazeTooManyRequestsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TvMazeRestRespondeErrorHandlerTest {

    private TvMazeRestRespondeErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new TvMazeRestRespondeErrorHandler();
    }

    @Test
    @DisplayName("hasError with HttpStatus series ClientError returns true")
    void hasError_ClientError_ReturnsTrue() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        when(response.getStatusCode()).thenReturn(httpStatus);
        assertThat(errorHandler.hasError(response)).isTrue();
    }


    @Test
    @DisplayName("hasError with HttpStatus series ServerError returns true")
    void hasError_ServerError_ReturnsTrue() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        when(response.getStatusCode()).thenReturn(httpStatus);
        assertThat(errorHandler.hasError(response)).isTrue();
    }

    @Test
    @DisplayName("hasError with HttpStatus OK returns false")
    void hasError_StatusOK_ReturnsFalse() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.OK;

        when(response.getStatusCode()).thenReturn(httpStatus);
        assertThat(errorHandler.hasError(response)).isFalse();
    }


    @Test
    @DisplayName("handleError with HttpStatus ServerError series throws TvMazeServerErrorException")
    void handleError_ServerError_throwsTvMazeServerErrorException() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        when(response.getStatusCode()).thenReturn(httpStatus);
        when(response.getStatusText()).thenReturn("TvMazeServerErrorException");

        assertThrows(TvMazeServerErrorException.class, () -> errorHandler.handleError(response));
    }

    @Test
    @DisplayName("handleError with HttpStatus.TOO_MANY_REQUESTS series throws TvMazeTooManyException")
    void handleError_TooManyRequest_ThrowsTvMazeTooManyRequestException() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;

        when(response.getStatusCode()).thenReturn(httpStatus);

        assertThrows(TvMazeTooManyRequestsException.class, () -> errorHandler.handleError(response));
    }


    @Test
    @DisplayName("handleError with HttpStatus.NOT_FOUND series throws TvMazeShowNotFoundException")
    void handleError_NotFound_ThrowsShowNotFoundException() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        when(response.getStatusCode()).thenReturn(httpStatus);

        assertThrows(TvMazeResourceNotFoundException.class, () -> errorHandler.handleError(response));
    }

    @Test
    @DisplayName("handleError with HttpStatus ClientError series throws TvMazeShowNotFoundException")
    void handleError_ClientError_ThrowsShowNotFoundException() throws IOException {
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        when(response.getStatusCode()).thenReturn(httpStatus);
        when(response.getStatusText()).thenReturn("TvMazeClientErrorException");

        assertThrows(TvMazeClientErrorException.class, () -> errorHandler.handleError(response));
    }

}