package com.teletorflix.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teletorflix.app.exceptions.SeasonNotFoundException;
import com.teletorflix.app.exceptions.ShowNotFoundException;
import com.teletorflix.app.model.Season;
import com.teletorflix.app.model.Show;
import com.teletorflix.app.repository.UserRepository;
import com.teletorflix.app.service.ShowService;
import com.teletorflix.app.utils.ShowFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ShowControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccessTokenFactory tokenFactory;

    @MockBean
    private ShowService showService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getShow_ValidShowId_ShouldReturnShow() throws Exception {
        String token = getToken();

        Show show = ShowFactory.getShowUptodateAiring();
        when(showService.getShowById(anyInt())).thenReturn(show);
        MvcResult result = this.mockMvc.perform(get(ShowController.SHOWS + ShowController.SHOW_ID, show.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        Show resultShow = objectMapper.readValue(result.getResponse().getContentAsString(), Show.class);

        assertThat(resultShow).isEqualTo(show);
    }

    private String getToken() throws Exception {
        return tokenFactory.createValidTokenMock(userRepository, mockMvc);
    }

    @Test
    void getShow_InvalidShowIßd_ShouldThrowShowNotFoundException() throws Exception {
        String token = getToken();

        when(showService.getShowById(anyInt())).thenThrow(new ShowNotFoundException());

        this.mockMvc.perform(get(ShowController.SHOWS + ShowController.SHOW_ID, 0)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getSeason_validShowIdAndValidSeasonNumber_ShouldReturnSeason() throws Exception {
        String token = getToken();

        final int showId = 1;
        final int seasonNumber = 3;

        when(showService.getSeasonById(showId, seasonNumber)).thenReturn(ShowFactory.getLastSeasonWithEpisodes());

        MvcResult result = this.mockMvc.perform(get(ShowController.SHOWS + ShowController.SHOW_ID +
                ShowController.SEASONS + ShowController.SEASON_NUMBER, showId, seasonNumber)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Season resultSeason = objectMapper.readValue(result.getResponse().getContentAsString(), Season.class);
        assertThat(resultSeason).isEqualTo(ShowFactory.getLastSeasonWithEpisodes());
    }

    @Test
    void getSeason_invalidShowId_ShouldThrowShowNotFoundException() throws Exception {
        String token = getToken();

        final int showId = 0;
        final int seasonNumber = 3;

        when(showService.getSeasonById(showId, seasonNumber)).thenThrow(new ShowNotFoundException());

        this.mockMvc.perform(get(ShowController.SHOWS + ShowController.SHOW_ID +
                ShowController.SEASONS + ShowController.SEASON_NUMBER, showId, seasonNumber)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getSeason_validShowIdAndInvalidSeasonNumber_ShouldThrowSeasonNotFoundException() throws Exception {
        String token = getToken();

        final int showId = 1;
        final int seasonNumber = 1;

        when(showService.getSeasonById(showId, seasonNumber)).thenThrow(new SeasonNotFoundException());

        this.mockMvc.perform(get(ShowController.SHOWS + ShowController.SHOW_ID +
                ShowController.SEASONS + ShowController.SEASON_NUMBER, showId, seasonNumber)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
