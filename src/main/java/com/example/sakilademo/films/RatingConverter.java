package com.example.sakilademo.films;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return switch (rating) {
            case PG_13 -> "PG-13";
            case NC_17 -> "NC-17";
            case null -> null;
            default -> rating.name();
        };
    }

    @Override
    public Rating convertToEntityAttribute(String input) {
        return switch (input) {
            case "PG-13" -> Rating.PG_13;
            case "NC-17" -> Rating.NC_17;
            case null -> null;
            default -> Rating.valueOf(input);
        };
    }
}
