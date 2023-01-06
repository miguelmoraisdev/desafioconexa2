package com.desafioconexa.miguelm.controller;

import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping(value = "/lukeskywalker")
    public ResponseEntity<String> getFilms(@RequestBody FilmRequest filmRequest){
        filmService.getListOfLukeFilms(filmRequest);
        return ResponseEntity.ok().body("Film saved!");
    }
}
