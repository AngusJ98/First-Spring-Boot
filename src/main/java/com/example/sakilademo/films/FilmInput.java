package com.example.sakilademo.films;

import com.example.sakilademo.actors.Actor;
import com.example.sakilademo.language.Language;
import com.example.sakilademo.validation.ValidationGroup;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import lombok.AccessLevel;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class FilmInput {

    @Size(min = 1, max = 128)
    @NotNull(groups = ValidationGroup.Create.class)
    private String title;

    private String description;
        private Year releaseYear;

    @NotNull(groups = ValidationGroup.Create.class)
    @Unsigned
    @Max(255)
    @Min(0)
    private int languageId;

    @Unsigned
    @Min(0)
    @Max(255)
    private Short originalLanguageid;

    @Min(0)
    @Max(255)
    @NotNull(groups = ValidationGroup.Create.class)
    private short rentalDuration;

    @Min(0)
    @NotNull(groups = ValidationGroup.Create.class)
    private BigDecimal rentalRate;

    @Min(0)
    @Unsigned
    private Short length;

    @NotNull(groups = ValidationGroup.Create.class)
    private BigDecimal replacementCost;
    
    private Rating rating;
    
    private List<SpecialFeature> specialFeatures;

}
