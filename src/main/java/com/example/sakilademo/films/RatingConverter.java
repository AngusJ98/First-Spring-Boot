package com.example.sakilademo.films;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        switch (rating) {
            case PG_13:
                return "PG-13";
            case NC_17:
                return "NC-17";
            default:
                return rating.name();
        }
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "PG-13":
                return Rating.PG_13;
            case "NC-17":
                return Rating.NC_17;
            default:
                return Rating.valueOf(dbData);
        }
    }
}
