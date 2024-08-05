package com.example.sakilademo.films;


import com.example.sakilademo.validation.ValidationGroup;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {


    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable short id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FilmResponse> createFilm(@RequestBody @Validated(ValidationGroup.Create.class) FilmInput input) {
        return new ResponseEntity<>(filmService.createFilm(input), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable short id) {
        return filmService.deleteFilm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@RequestBody @Validated(ValidationGroup.Create.class) FilmInput filmData, @PathVariable short id) {
        return new ResponseEntity<>(filmService.updateFilm(id, filmData), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FilmResponse> patchFilm(@RequestBody FilmInput filmData, @PathVariable short id) {
        return new ResponseEntity<>(filmService.patchFilm(id, filmData), HttpStatus.OK);
    }





}
