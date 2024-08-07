package com.example.sakilademo.films;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Year;

@Getter
@EqualsAndHashCode
public class PartialFilmResponse {
    private final Short id;
    private final String title;
    private final Year releaseYear;

    public PartialFilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
    }
}
