package com.teletorflix.app.service;

import com.teletorflix.app.dtos.TvMazeShowDto;
import com.teletorflix.app.exceptions.TvMazeShowNotFoundException;
import com.teletorflix.app.model.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TvMazServiceTest {

    private TvMazeService tvMazeService;

    @Mock
    private TvMazeClient tvMazeClient;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.tvMazeService = new TvMazeServiceImpl(tvMazeClient, modelMapper);
    }

    @Test
    @DisplayName("Fetch Show by Id should return Show instance with same Id")
    void fetchShow_ById_shouldReturnShow() {
        int id = 1;
        TvMazeShowDto tvMazeShowDto = Mockito.mock(TvMazeShowDto.class);
        Show show = Mockito.mock(Show.class);
        when(tvMazeClient.getShowById(id)).thenReturn(tvMazeShowDto);
        when(modelMapper.map(tvMazeShowDto, Show.class)).thenReturn(show);

        Show returnedShow = tvMazeService.getShowById(id);

        assertThat(returnedShow).isEqualTo(show);
    }

    @Test
    @DisplayName("Fetch Non-Existent Show Throws TvMazeShowNotFoundException")
    void fetchShow_NonExistentShow_shouldThrowShowNotFoundException() {
        int id = 1;
        TvMazeShowDto tvMazeShowDto = Mockito.mock(TvMazeShowDto.class);
        Show show = Mockito.mock(Show.class);
        when(tvMazeClient.getShowById(id)).thenThrow(TvMazeShowNotFoundException.class);

        assertThrows(TvMazeShowNotFoundException.class, () -> tvMazeService.getShowById(id));
    }

    @Test
    @DisplayName("Fetch Show should call TvMazeConfig's GetShowByIdURL method")
    void fetchShow_ById_shouldCallTvMazeConfigGetShowByIdUrl() {
        int id = 1;
        TvMazeShowDto tvMazeShowDto = Mockito.mock(TvMazeShowDto.class);
        Show show = Mockito.mock(Show.class);

        when(tvMazeClient.getShowById(id)).thenReturn(tvMazeShowDto);

        when(modelMapper.map(tvMazeShowDto, Show.class)).thenReturn(show);

        tvMazeService.getShowById(id);

        verify(tvMazeClient).getShowById(id);
    }

}
