package com.example.sakilademo.films;


import com.example.sakilademo.actors.Actor;
import com.example.sakilademo.language.Language;
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
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    @Setter(AccessLevel.NONE)

    @Column(name = "film_id")
    private short id;


    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @Column(name = "release_year")
    private Year releaseYear;

    //private short languageId;



    @ManyToOne
    @JoinColumn (name = "language_id")
    private Language language;


    @Column(name = "original_language_id")
    private Short originalLanguageid;


    @Column(name = "rental_duration")
    private short rentalDuration;


    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Unsigned
    @Column(name = "length")
    private Short length;


    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

    @Convert(converter = SpecialFeaturesConverter.class)
    @Column(name = "special_features")
    private List<SpecialFeature> specialFeatures;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ManyToMany(mappedBy = "films")
    private List<Actor> cast = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }


    public Film (FilmInput data) {
        BeanUtils.copyProperties(data, this);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", languageid=" + language +
                ", originalLanguageid=" + originalLanguageid +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating=" + rating +
                ", specialFeatures=" + specialFeatures +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}


