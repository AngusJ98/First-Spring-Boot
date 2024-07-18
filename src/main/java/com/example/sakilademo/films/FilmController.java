package com.example.sakilademo.films;


import com.example.sakilademo.validation.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable short id) {
        return filmService.getFilmById(id);
    }

    @GetMapping
    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    public ResponseEntity<FilmResponse> createFilm(FilmInput data) {
        return filmService.createFilm(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable short id) {
        return filmService.deleteFilm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@RequestBody @Validated(ValidationGroup.Create.class) FilmInput filmData, @PathVariable short id) {
        return filmService.updateFilm(id, filmData);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FilmResponse> patchFilm(@RequestBody @Validated FilmInput filmData, @PathVariable short id) {
        return filmService.patchFilm(id, filmData);
    }









}
