package com.desafioconexa.miguelm.controller;

import com.desafioconexa.miguelm.dto.FilmRequest;
import com.desafioconexa.miguelm.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Operation(summary = "Add new film", description = "Add new film of Luke Skywalker on the list",tags = "Post")
    @PostMapping(value = "/lukeskywalker")
    public ResponseEntity<String> getFilmsOfLuke(@Valid @RequestBody FilmRequest filmRequest){
        filmService.getListOfLukeFilms(filmRequest);
        return ResponseEntity.ok().body("Film saved!");
    }
}
