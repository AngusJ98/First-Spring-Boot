package com.example.sakilademo.films;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SpecialFeaturesConverter implements AttributeConverter<List<SpecialFeature>, String> {


    @Override
    public String convertToDatabaseColumn(List<SpecialFeature> specialFeatures) throws IllegalArgumentException{
        List<String> strings =  specialFeatures.stream().map(this::stringFromEnum).filter(a -> !a.isEmpty()).toList();
        if (strings.isEmpty()) return "";

        HashSet<String> hashedStrings = new HashSet<>(strings);
        if (hashedStrings.size() != strings.size()) {
            throw new IllegalArgumentException("Duplicates found in special features");
        }
        return String.join(",", strings);
    }

    @Override
    public List<SpecialFeature> convertToEntityAttribute(String s) {
        List<String> strings = List.of(s.split(","));
        List<String> filteredStrings = strings.stream().filter(a -> !a.isEmpty()).toList();
        return filteredStrings.stream().map(this::featureFromString).filter(Objects::nonNull).toList();
    }

    private SpecialFeature featureFromString(String s) throws IllegalArgumentException{
        return switch (s){
             case "Trailers" -> SpecialFeature.TRAILERS;
             case "Commentaries" -> SpecialFeature.COMMENTARIES;
             case "Deleted Scenes" -> SpecialFeature.DELETED_SCENES;
             case "Behind the Scenes" -> SpecialFeature.BEHIND_THE_SCENES;
             //case "" -> null;
             default -> throw new IllegalArgumentException("Feature not accepted");
        };
    }

    private String stringFromEnum(SpecialFeature sf) {
        return switch (sf){
            case TRAILERS -> "Trailers";
            case COMMENTARIES -> "Commentaries";
            case DELETED_SCENES -> "Deleted Scenes";
            case BEHIND_THE_SCENES -> "Behind the Scenes";
            case null -> "";
        };
    }

}
