package com.desafioconexa.miguelm.service;

import com.desafioconexa.miguelm.client.RestFeignClient;
import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.entity.Film;
import com.desafioconexa.miguelm.repository.FilmRepository;
import com.desafioconexa.miguelm.service.exceptions.FeignClientException;
import com.desafioconexa.miguelm.service.exceptions.FilmAlreadyExistsInDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FilmServiceTest {

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RestFeignClient restFeignClient;

    private FilmRequest filmRequest1;
    private FilmRequest filmRequest2;
    private FilmRequest filmRequest3;
    private FilmRequest filmToSave;
    private Film film1;
    private Film film2;
    private Film filmSaved;
    private Set<FilmRequest> clientList = new HashSet<>();
    private List<Film> savedFilms = new ArrayList<>();
    private List<Film> emptyList = new ArrayList<>();
    private Set<Film> setSavedFilms = new HashSet<>();
    private List<FilmRequest> listClient = new ArrayList<>();
    private Page<FilmRequest> page;

    @BeforeEach
    void setUp() throws Exception {
        filmRequest1 = new FilmRequest("Star Wars 1", "1", "George Lucas", new Date());
        filmRequest2 = new FilmRequest("Star Wars 2", "2", "George Lucas", new Date());
        filmRequest3 = new FilmRequest("Star Wars 3", "3", "George Lucas", new Date());
        film1 = new Film("Star Wars 1", "1", "George Lucas", new Date());
        film2 = new Film("Star Wars 2", "2", "George Lucas", new Date());
        listClient.add(filmRequest1);
        listClient.add(filmRequest2);
        listClient.add(filmRequest3);
        clientList.add(filmRequest2);
        clientList.add(filmRequest3);
        savedFilms.add(film1);
        savedFilms.add(film2);
        setSavedFilms.add(film1);
        setSavedFilms.add(film2);
        filmToSave = new FilmRequest("Star Wars 4", "4", "George Lucas", new Date());
        filmSaved = new Film("Star Wars 4", "4", "George Lucas", new Date());
        page = new PageImpl<>(listClient);
        when(restFeignClient.getFilmsList()).thenReturn(page);
        when(filmRepository.findAll()).thenReturn(savedFilms);
        when(filmRepository.save(any())).thenReturn(filmSaved);
    }

    @Test
    void testGetListOfLukeFilms() {
        Assertions.assertDoesNotThrow(() -> {
            filmService.getListOfLukeFilms(filmToSave);
        });

        when(filmRepository.findAll()).thenReturn(emptyList);
        Assertions.assertDoesNotThrow(() -> {
            filmService.getListOfLukeFilms(filmToSave);
        });
    }

    @Test
    void testGetFilmsFromListClient() {
        Assertions.assertEquals(2, filmService.getFilmsFromListClient(clientList).size());
    }

    @Test
    void testGetSetFromList() {
        Assertions.assertEquals(2, filmService.getSetFromList(savedFilms).size());
    }

    @Test
    void testCompareFilmRequestWithFeignClient() {
        //No Exception case
        Assertions.assertDoesNotThrow(() -> {
            filmService.compareFilmRequestWithFeignClient(clientList, filmToSave);
        });

        //Exception case
        filmToSave.setEpisodeId("2");
        Assertions.assertThrows(FilmAlreadyExistsInDatabase.class, () -> {
            filmService.compareFilmRequestWithFeignClient(clientList, filmToSave);
        } );
    }

    @Test
    void testCompareFilmRequestWithFilmsSaved() {
        //No Exception case
        Assertions.assertDoesNotThrow(() -> {
            filmService.compareFilmRequestWithFilmsSaved(savedFilms, filmToSave);
        });

        //Exception case
        filmToSave.setEpisodeId("1");
        Assertions.assertThrows(FilmAlreadyExistsInDatabase.class, () -> {
            filmService.compareFilmRequestWithFilmsSaved(savedFilms, filmToSave);
        } );

    }

    @Test
    void testGetFilmsFromPage() {
        //No Exception case
        Set<FilmRequest> listResult = filmService.getFilmsFromPage();

        assertNotNull(listResult);
        assertEquals(3, listResult.size());

        //Exception case
        when(restFeignClient.getFilmsList()).thenThrow(FeignClientException.class);
        Assertions.assertThrows(FeignClientException.class, () -> {
            filmService.getFilmsFromPage();
        } );
    }

    @Test
    void testSavedFilmsIsEmpty() {
        Set<Film> listResult = filmService.savedFilmsIsEmpty(clientList, filmToSave);

        assertNotNull(listResult);
        assertEquals(3, listResult.size());

    }
}
