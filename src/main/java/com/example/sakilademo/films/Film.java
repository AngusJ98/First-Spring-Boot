package com.example.sakilademo.films;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jdk.jfr.Unsigned;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Target;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "film", schema = "sakila")
public class Film {

    public Film () {
        this.lastUpdate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }



    @Id
    @Unsigned
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @NotNull
    @Column(name = "film_id")
    private short id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @Column(name = "release_year")
    private Year releaseYear;

    @NotNull
    @Unsigned
    @Column(name = "language_id")
    private short languageid;

    @Unsigned
    @Nullable
    @Column(name = "original_language_id")
    private Short originalLanguageid;

    @NotNull
    @Column(name = "rental_duration")
    private short rentalDuration;

    @NotNull
    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @NotNull
    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

    @Column(name = "special_features")
    private String specialFeatures;

    @NotNull
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}


