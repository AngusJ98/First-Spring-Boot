package com.example.sakilademo.films;


import com.example.sakilademo.language.Language;
import com.example.sakilademo.validation.ValidationGroup;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
public class FilmInput {

    @Size(min = 1, max = 128)
    @NotNull(groups = ValidationGroup.Create.class)
    private String title;

    private String description;
        private Year releaseYear;

    @NotNull(groups = ValidationGroup.Create.class)
    @Max(255)
    @Min(1)
    private short languageId;

    private Short originalLanguageId;

    private Language originalLanguage;

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

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Convert(converter = SpecialFeaturesConverter.class)
    private List<SpecialFeature> specialFeatures;

    private List<Short> castIds;
}
