package com.example.sakilademo.films;

import com.example.sakilademo.films.Film;
import com.example.sakilademo.films.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/films/")
    public List<Film> readAll() {
        return filmRepository.findAll();
    }


}