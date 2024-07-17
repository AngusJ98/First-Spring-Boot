package com.example.sakilademo.films;

import com.example.sakilademo.films.Film;
import com.example.sakilademo.films.FilmRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/film/{id}")
    public Film getFilmById(@PathVariable short id){
        return (filmRepository.findById(id));
    }

    @GetMapping("/film/")
    public List<Film> getFilms() {
        returnfilmRepository.findAll();
    }

    @DeleteMapping("/film/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable short id) {
        try {
           filmRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/film/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable short id, @RequestBody Film film) {
        Film exist =filmRepository.findById(id);
        if (exist != null) {
            BeanUtils.copyProperties(film, exist);
            return ResponseEntity.ok(filmRepository.save(exist));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/film/")
    public ResponseEntity<Film> newFilm(@RequestBody Film film) {
        return ResponseEntity.ok(filmRepository.save(film));
    }

    @PatchMapping("/film/{id}")
    public  ResponseEntity<Film> patchFilm(@PathVariable short id, @RequestBody Map<String, String> newData) {
        Film film = filmRepository.findById(id);
        if (film != null) {
            newData.forEach((a, b) -> {
                switch (a) {
                    case "firstName":
                       film.setFirstName(b);
                        break;
                    case "lastName":
                       film.setLastName(b);
                        break;
                }
            });

            return ResponseEntity.ok(filmRepository.save(film));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}