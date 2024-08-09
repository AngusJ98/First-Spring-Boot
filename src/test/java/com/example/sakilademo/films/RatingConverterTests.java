package com.example.sakilademo.films;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RatingConverterTests {

    private RatingConverter ratingConverter;

    @BeforeEach
    void setUp() {
        ratingConverter = new RatingConverter();
    }

    @Test
    void testConvertToDatabaseColumn() {
        // Test converting specific enum values to their string representation
        Assertions.assertEquals("PG-13", ratingConverter.convertToDatabaseColumn(Rating.PG_13));
        Assertions.assertEquals("NC-17", ratingConverter.convertToDatabaseColumn(Rating.NC_17));
        Assertions.assertEquals("G", ratingConverter.convertToDatabaseColumn(Rating.G)); // default case
        Assertions.assertEquals("R", ratingConverter.convertToDatabaseColumn(Rating.R)); // default case

        // Test converting null
        Assertions.assertNull(ratingConverter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        // Test converting string values back to enum values
        Assertions.assertEquals(Rating.PG_13, ratingConverter.convertToEntityAttribute("PG-13"));
        Assertions.assertEquals(Rating.NC_17, ratingConverter.convertToEntityAttribute("NC-17"));
        Assertions.assertEquals(Rating.G, ratingConverter.convertToEntityAttribute("G"));
        Assertions.assertEquals(Rating.R, ratingConverter.convertToEntityAttribute("R"));

        // Test converting null
        Assertions.assertNull(ratingConverter.convertToEntityAttribute(null));

        // Test converting an invalid string (should throw IllegalArgumentException)
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ratingConverter.convertToEntityAttribute("INVALID"));
    }
}