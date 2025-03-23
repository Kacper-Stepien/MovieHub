package com.example.movies_api.flyweight;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class MovieTypeConverter implements AttributeConverter<MovieType, String> {

    @Override
    public String convertToDatabaseColumn(MovieType attribute) {
        return (attribute == null) ? null : attribute.type();
    }

    @Override
    public MovieType convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : MovieType.valueOf(dbData);
    }
}