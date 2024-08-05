package com.example.sakilademo.films;

import com.example.sakilademo.actors.PartialActorResponse;
import com.example.sakilademo.language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;


@Getter
@AllArgsConstructor
public class FilmResponse {

    private final Short id;
    private final String title;
    private final Year releaseYear;
    private final Language language;
    private final List<PartialActorResponse> cast;
    private Language originalLanguageid;
    private String description;
    private short rentalDuration;
    private BigDecimal rentalRate;
    private Short length;
    private BigDecimal replacementCost;
    private Rating rating;
    private List<SpecialFeature> specialFeatures;
    private LocalDateTime lastUpdate;




    public FilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.language = film.getLanguage();
        this.cast = film.getCast().stream().map(PartialActorResponse::new).toList();
        this.originalLanguageid = film.getOriginalLanguage();
        this.description = film.getDescription();
        this.rentalDuration = film.getRentalDuration();
        this.rentalRate = film.getRentalRate();
        this.length = film.getLength();
        this.replacementCost = film.getReplacementCost();
        this.rating = film.getRating();
        this.specialFeatures = film.getSpecialFeatures();
        this.lastUpdate = film.getLastUpdate();
    }
}
