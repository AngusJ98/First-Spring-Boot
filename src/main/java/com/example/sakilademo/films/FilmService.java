package com.example.sakilademo.films;


import com.example.sakilademo.actors.ActorRepository;
import com.example.sakilademo.language.Language;
import com.example.sakilademo.language.LanguageRepository;
import com.example.sakilademo.validation.ValidationGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyDescriptor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ActorRepository actorRepository;

    public ResponseEntity<FilmResponse> getFilmById(short id){
        Film film = filmRepository.findById(id);
        if (film != null) {
            return ResponseEntity.ok(new FilmResponse(film));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        return ResponseEntity.ok(filmRepository.findAll().stream().map(FilmResponse::new).toList());
    }

    public ResponseEntity<HttpStatus> deleteFilm(short id) {
        Film film = filmRepository.findById(id);
        if (film != null) {
            try {
                filmRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<FilmResponse> updateFilm(@PathVariable short id, @Validated(ValidationGroup.Create.class) @RequestBody FilmInput filmData) {

        Film film = filmRepository.findById(id);
        if (film != null) {
            try {
                BeanUtils.copyProperties(filmData, film);
                return ResponseEntity.ok(new FilmResponse(filmRepository.save(film)));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<FilmResponse> createFilm(@RequestBody @Validated(ValidationGroup.Create.class) FilmInput filmData) {
        Film film = new Film(filmData);
        Language language = languageRepository.findById(filmData.getLanguageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Language not found, or invalid language code entered"));
        film.setLanguage(language);
        if (filmData.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmData.getOriginalLanguageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Original language does not exist"));
            film.setOriginalLanguage(originalLanguage);
        }


        for (Short castId : filmData.getCastIds()) {
            film.getCast().add(actorRepository.findById(castId).orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        }

        return ResponseEntity.ok(new FilmResponse(filmRepository.save(film)));
    }

    public  ResponseEntity<FilmResponse> patchFilm(@PathVariable short id, @RequestBody @Validated FilmInput filmData) {

        Film film = filmRepository.findById(id);
        if (film != null ) {
            BeanUtils.copyProperties(filmData, film);
            return ResponseEntity.ok(new FilmResponse(filmRepository.save(film)));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film at id " + id + " not found.");
        }
            //what is a bean
            //what is a bean wrapper
//            //All I know is that this works
//            BeanWrapper wrap = new BeanWrapperImpl(film);
//
//            newData.forEach((a, b) -> {
//                try {
//                    PropertyDescriptor pd = wrap.getPropertyDescriptor(a);
//                    //Method writeMethod = pd.getWriteMethod();
//                    //do the writing
//                    if (pd.getWriteMethod() != null) {
//                        wrap.setPropertyValue(a, b);
//                        //ReflectionUtils.makeAccessible(writeMethod);
//                        //writeMethod.invoke(film, b);
//                    }
//                } catch (Exception e) {
//                    throw new IllegalArgumentException("Either the key or value were invalid");
//                }
//            });
    }




}