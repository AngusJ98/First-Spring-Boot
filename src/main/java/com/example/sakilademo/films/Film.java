package com.example.sakilademo.films;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Target;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@Table(name = "film", schema = "sakila")
public class Film {

    public Film () {
        this.lastUpdate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "language_id")
    private short languageid;

    @Column(name = "original_language_id")
    private short originalLanguageid;

    @Column(name = "rental_duration")
    private short rentalDuration;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Column(name = "length")
    private short length;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private Rating rating;

    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}


enum Rating {
    G,
    PG,
    PG_13,
    R,
    NC_17
}