package com.desafioconexa.miguelm.service;

import com.desafioconexa.miguelm.client.RestFeignClient;
import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.entity.Film;
import com.desafioconexa.miguelm.repository.FilmRepository;
import com.desafioconexa.miguelm.service.exceptions.FeignClientException;
import com.desafioconexa.miguelm.service.exceptions.FilmAlreadyExistsInDatabase;
import feign.FeignException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FilmService {

    @Autowired
    private RestFeignClient restFeignClient;

    @Autowired
    private FilmRepository filmRepository;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FilmService.class);

    public void getListOfLukeFilms(FilmRequest filmToSave) {
        List<Film> filmsToPrint = new ArrayList<>();
        List<FilmRequest> listClient = getFilmsFromPage();
        compareFilmRequestWithFeignClient(listClient, filmToSave);
        List<Film> savedFilms = filmRepository.findAll();
        if (savedFilms.isEmpty()) {
            filmsToPrint = savedFilmsIsEmpty(listClient, filmToSave);
            System.out.println(filmsToPrint);
            log.info("Films saved!");
        } else {
            compareFilmRequestWithFilmsSaved(savedFilms, filmToSave);
            filmsToPrint.addAll(savedFilms);
            filmsToPrint.addAll(checkIfAnyMatch(savedFilms, listClient, filmToSave));
            System.out.println(filmsToPrint);
            log.info("Film saved!");
        }

    }

    protected void compareFilmRequestWithFeignClient(List<FilmRequest> listClient, FilmRequest filmToSave) {
        for (FilmRequest filmRequest : listClient) {
            if (filmRequest.getEpisodeId().equals(filmToSave.getEpisodeId())) {
                log.error("Film Already exists in database");
                throw new FilmAlreadyExistsInDatabase("Film Already exists in database");

            }
        }
    }

    protected void compareFilmRequestWithFilmsSaved(List<Film> savedFilms, FilmRequest filmToSave) {
        for (Film film : savedFilms) {
            if (film.getEpisodeId().equals(filmToSave.getEpisodeId())) {
                log.error("Film Already exists in database");
                throw new FilmAlreadyExistsInDatabase("Film Already exists in database");
            }
        }
    }

    protected List<FilmRequest> getFilmsFromPage() {
        try {
            Page<FilmRequest> page = restFeignClient.getFilmsList();
            List<FilmRequest> listClient = page.getContent();
            return listClient;
        } catch (FeignException e) {
            log.error("Time out for the client");
            throw new FeignClientException("Time out for the client");
        }
    }

    protected List<Film> checkIfAnyMatch(List<Film> savedFilms, List<FilmRequest> listClient, FilmRequest filmToSave) {
        List<Film> filmsToSave = new ArrayList<>();
        for (FilmRequest filmRequest : listClient) {
            if (!savedFilms.contains(new Film(filmRequest.getTitle(), filmRequest.getEpisodeId(), filmRequest.getDirector(), filmRequest.getReleaseDate()))) {
                filmsToSave.add(new Film(filmToSave.getTitle(), filmRequest.getEpisodeId(), filmRequest.getDirector(), filmRequest.getReleaseDate()));
            }
        }

        filmsToSave.add(new Film(filmToSave.getTitle(),
                filmToSave.getEpisodeId(),
                filmToSave.getDirector(),
                filmToSave.getReleaseDate()));

        filmsToSave.stream().forEach(film -> filmRepository.save(film));
        return filmsToSave;
    }

    protected List<Film> savedFilmsIsEmpty(List<FilmRequest> listClient, FilmRequest filmRequest) {
        List<Film> filmsToSave = new ArrayList<>();
        listClient.stream().forEach((FilmRequest film) -> {
            filmsToSave.add(new Film(film.getTitle(), film.getEpisodeId(), film.getDirector(), film.getReleaseDate()));
        });
        filmsToSave.add(new Film(filmRequest.getTitle(),
                filmRequest.getEpisodeId(),
                filmRequest.getDirector(),
                filmRequest.getReleaseDate()));
        filmsToSave.stream().forEach(film -> filmRepository.save(film));

        return filmsToSave;
    }

}
