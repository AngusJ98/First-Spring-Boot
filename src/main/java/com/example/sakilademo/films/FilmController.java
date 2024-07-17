package com.example.sakilademo.films;

import com.example.sakilademo.films.Film;
import com.example.sakilademo.films.FilmRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        return filmRepository.findAll();
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
            try {
                BeanUtils.copyProperties(film, exist);
                return ResponseEntity.ok(filmRepository.save(exist));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/film/")
    public ResponseEntity<Film> newFilm(@RequestBody Film film) {
        return ResponseEntity.ok(filmRepository.save(film));
    }

    @PatchMapping("/film/{id}")
    public  ResponseEntity<Film> patchFilm(@PathVariable short id, @RequestBody Map<String, Object> newData) {

        Film film = filmRepository.findById(id);

        if (film != null ) {

            //what is a bean
            //what is a bean wrapper
            //All I know is that this works
            BeanWrapper wrap = new BeanWrapperImpl(film);

            newData.forEach((a, b) -> {
                try {
                    PropertyDescriptor pd = wrap.getPropertyDescriptor(a);
                    //Method writeMethod = pd.getWriteMethod();
                    //do the writing
                    if (pd.getWriteMethod() != null) {
                        wrap.setPropertyValue(a, b);
                        //ReflectionUtils.makeAccessible(writeMethod);
                        //writeMethod.invoke(film, b);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Either the key or value were invalid");
                }
            });

            return ResponseEntity.ok(filmRepository.save(film));
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}