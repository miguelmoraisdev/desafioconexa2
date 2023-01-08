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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {

    @Autowired
    private RestFeignClient restFeignClient;

    @Autowired
    private FilmRepository filmRepository;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FilmService.class);

    public void getListOfLukeFilms(FilmRequest filmToSave) {
        Set<Film> filmsToPrint = new HashSet<>();
        Set<FilmRequest> listClient = getFilmsFromPage();
        compareFilmRequestWithFeignClient(listClient, filmToSave);
        List<Film> savedFilms = filmRepository.findAll();
        if (savedFilms.isEmpty()) {
            filmsToPrint = savedFilmsIsEmpty(listClient, filmToSave);
            System.out.println(filmsToPrint);
            log.info("Films saved!");
        } else {
            compareFilmRequestWithFilmsSaved(savedFilms, filmToSave);
            Set<Film> savedFilmsSet = getSetFromList(savedFilms);
            filmsToPrint.add(new Film(filmToSave.getTitle(), filmToSave.getEpisodeId(), filmToSave.getDirector(), filmToSave.getReleaseDate()));
            filmsToPrint.addAll(savedFilmsSet);
            filmsToPrint.addAll(getFilmsFromListClient(listClient));
            filmsToPrint.stream().forEach((Film film) -> {
                filmRepository.save(film);
            });
            log.info("Film saved!");
            System.out.println(filmsToPrint);
        }
    }

    protected Set<Film> getFilmsFromListClient(Set<FilmRequest> listClient) {
        Set<Film> clientFilms = new HashSet<>();
        listClient.stream().forEach((FilmRequest filmRequest) -> {
            clientFilms.add(new Film(filmRequest.getTitle(), filmRequest.getEpisodeId(), filmRequest.getDirector(), filmRequest.getReleaseDate()));
        });
        return clientFilms;
    }
    protected Set<Film> getSetFromList(List<Film> savedFilms) {
        Set<Film> setFilms = new HashSet<>();
        savedFilms.stream().forEach((Film film) -> {
            setFilms.add(film);
        });
        return setFilms;
    }
    protected void compareFilmRequestWithFeignClient(Set<FilmRequest> listClient, FilmRequest filmToSave) {
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

    protected Set<FilmRequest> getFilmsFromPage() {
        try {
            Set<FilmRequest> setFilms = new HashSet<>();
            Page<FilmRequest> page = restFeignClient.getFilmsList();
            page.getContent().stream().forEach((FilmRequest filmRequest) -> {
                setFilms.add(filmRequest);
            });
            return setFilms;
        } catch (FeignException e) {
            log.error("Time out for the client");
            throw new FeignClientException("Time out for the client");
        }
    }

    protected Set<Film> savedFilmsIsEmpty(Set<FilmRequest> listClient, FilmRequest filmRequest) {
        Set<Film> filmsToSave = new HashSet<>();
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
