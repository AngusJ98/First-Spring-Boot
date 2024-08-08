package com.example.sakilademo.films;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@Converter(autoApply = true)
public class SpecialFeaturesConverter implements AttributeConverter<List<SpecialFeature>, String> {


    @Override
    public String convertToDatabaseColumn(List<SpecialFeature> specialFeatures) throws IllegalArgumentException{
        if (specialFeatures == null) {
            return null;
        }
        specialFeatures.forEach( a -> {
            if (Objects.isNull(a)) {
                throw new IllegalArgumentException("Special features cannot be null");
            }
        });
        List<String> strings =  specialFeatures.stream().map(this::stringFromEnum).toList();
        //System.out.println(strings);
        if (strings.isEmpty()) {
            return "";
        }

        HashSet<String> hashedStrings = new HashSet<>(strings);
        if (hashedStrings.size() != strings.size()) {
            throw new IllegalArgumentException("Duplicates found in special features");
        }
        return String.join(",", strings);
    }

    @Override
    public List<SpecialFeature> convertToEntityAttribute(String s) {
        List<String> strings = List.of(s.split(","));
        return strings.stream().filter(a -> !a.isEmpty()).map(this::featureFromString).toList();
    }

    private SpecialFeature featureFromString(String s) throws IllegalArgumentException{
        return switch (s){
             case "Trailers" -> SpecialFeature.TRAILERS;
             case "Commentaries" -> SpecialFeature.COMMENTARIES;
             case "Deleted Scenes" -> SpecialFeature.DELETED_SCENES;
             case "Behind the Scenes" -> SpecialFeature.BEHIND_THE_SCENES;
             default -> throw new IllegalArgumentException("Feature not accepted");
        };
    }

    private String stringFromEnum(SpecialFeature sf) {
        return switch (sf){
            case TRAILERS -> "Trailers";
            case COMMENTARIES -> "Commentaries";
            case DELETED_SCENES -> "Deleted Scenes";
            case BEHIND_THE_SCENES -> "Behind the Scenes";
        };
    }

}
