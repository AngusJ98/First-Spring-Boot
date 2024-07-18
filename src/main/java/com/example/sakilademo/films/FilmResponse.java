package com.example.sakilademo.films;

import com.example.sakilademo.actors.PartialActorResponse;
import com.example.sakilademo.language.Language;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


@Getter
public class FilmResponse {

    private final Short id;
    private final String title;
    private final Year releaseYear;
    private final Language language;
    private final List<PartialActorResponse> cast;

    public FilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.language = film.getLanguage();
        this.cast = film.getCast().stream().map(PartialActorResponse::new).toList();
    }
}
