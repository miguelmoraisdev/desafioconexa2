package com.desafioconexa.miguelm.controller;

import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.service.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    private FilmRequest filmRequest1;

    @BeforeEach
    void setUp() throws Exception {
        filmRequest1 = new FilmRequest("Star Wars 1", "1", "George Lucas", new Date());
    }

    @Test
    void testGetFilmsOfLuke() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(filmRequest1);

        ResultActions result =
                mockMvc.perform(post("/film/lukeskywalker")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }
}
