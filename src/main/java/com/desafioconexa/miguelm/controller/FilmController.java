package com.desafioconexa.miguelm.controller;

import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping(value = "/lukeskywalker")
    public ResponseEntity<String> getFilmsOfLuke(@RequestBody FilmRequest filmRequest){
        filmService.getListOfLukeFilms(filmRequest);
        return ResponseEntity.ok().body("Film saved!");
    }
}
