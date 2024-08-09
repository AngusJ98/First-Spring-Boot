package com.example.sakilademo.films;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpecialFeaturesConverterTest {
    private SpecialFeaturesConverter converter;
    @BeforeEach
    void setUp() {
        converter = new SpecialFeaturesConverter();
    }

    @Test
    void convertToDatabaseColumn_WithValid() {
        List<SpecialFeature> features = List.of(SpecialFeature.TRAILERS, SpecialFeature.COMMENTARIES);
        String result = converter.convertToDatabaseColumn(features);
        assertEquals("Trailers,Commentaries", result);
    }

    @Test
    void testConvertToDatabaseColumn_WithEmptyList() {
        List<SpecialFeature> features = List.of();
        String result = converter.convertToDatabaseColumn(features);
        assertEquals("", result);
    }

    @Test
    void testConvertToDatabaseColumn_WithNullList() {
        String result = converter.convertToDatabaseColumn(null);
        assertNull(result);
    }

    @Test
    void testConvertToDatabaseColumn_WithNullFeature() {
        List<SpecialFeature> features = List.of(SpecialFeature.TRAILERS, null);
        assertThrows(IllegalArgumentException.class, () -> converter.convertToDatabaseColumn(features));
    }

    @Test
    void testConvertToDatabaseColumn_WithDuplicateFeatures() {
        List<SpecialFeature> features = List.of(SpecialFeature.TRAILERS, SpecialFeature.TRAILERS);
        assertThrows(IllegalArgumentException.class, () -> converter.convertToDatabaseColumn(features));
    }

    @Test
    void testConvertToEntityAttribute_WithValidString() {
        String featureString = "Trailers,Commentaries";
        List<SpecialFeature> result = converter.convertToEntityAttribute(featureString);
        assertEquals(List.of(SpecialFeature.TRAILERS, SpecialFeature.COMMENTARIES), result);
    }

    @Test
    void testConvertToEntityAttribute_WithEmptyString() {
        String featureString = "";
        List<SpecialFeature> result = converter.convertToEntityAttribute(featureString);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertToEntityAttribute_WithInvalidFeatureString() {
        String featureString = "InvalidFeature";
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(featureString));
    }

    @Test
    void testConvertToEntityAttribute_WithNullString() {
        String featureString = null;
        assertThrows(NullPointerException.class, () -> converter.convertToEntityAttribute(featureString));
    }
}