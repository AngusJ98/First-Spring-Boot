package com.example.sakilademo.films;



import com.example.sakilademo.actors.ActorRepository;
import com.example.sakilademo.language.Language;
import com.example.sakilademo.language.LanguageRepository;
import com.example.sakilademo.utility.Utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class FilmService {

    private final FilmRepository filmRepository;

    private final LanguageRepository languageRepository;

    private final ActorRepository actorRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository, LanguageRepository languageRepository, ActorRepository actorRepository) {
        this.filmRepository = filmRepository;
        this.languageRepository = languageRepository;
        this.actorRepository = actorRepository;
    }

    public FilmResponse getFilmById(short id){
        try {
            return new FilmResponse(filmRepository.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public List<FilmResponse> getAllFilms() {
        return filmRepository.findAll().stream().map(FilmResponse::new).toList();
    }

    public ResponseEntity<HttpStatus> deleteFilm(short id) {

        try {
            filmRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            //e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public FilmResponse updateFilm(short id, FilmInput filmData) {
        try {
            Film film = filmRepository.findById(id);
            if (film != null) {
                BeanUtils.copyProperties(filmData, film);
                return new FilmResponse(filmRepository.save(film));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    public FilmResponse createFilm(FilmInput filmData) {
        Film film = new Film(filmData);

        Language language = languageRepository.findById(filmData.getLanguageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Language not found, or invalid language code entered"));
        film.setLanguage(language);
        if (filmData.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmData.getOriginalLanguageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Original language does not exist"));
            film.setOriginalLanguage(originalLanguage);
        }

        if (filmData.getCastIds() != null) {
            for (Short castId : filmData.getCastIds()) {
                film.getCast().add(actorRepository.findById(castId).orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor not found at ID: " + castId)));
            }
        }
        return new FilmResponse(filmRepository.save(film));
    }

    public  FilmResponse patchFilm(short id, FilmInput filmData) {

        Film film = filmRepository.findById(id);

        List<String> ignored = new ArrayList<>();
        ignored.add("castIds");
        ignored.add("languageId");
        ignored.add("rating");
        //Use the cool function I wrote to copy all the non-annoying non-null properties to the film
        Utils.copyNonNullProperties(filmData, film, ignored);

        //TODO Write logic to copy cast and language and rating

        return new FilmResponse(filmRepository.save(film));

    }




}