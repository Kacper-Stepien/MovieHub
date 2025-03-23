package com.example.movies_api.flyweight;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RatingValueConverter implements AttributeConverter<RatingValue, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RatingValue attribute) {
        if (attribute == null) return null;
        return attribute.value(); // z ratingValue(5) -> 5
    }

    @Override
    public RatingValue convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return RatingValue.of(dbData); // 5 -> RatingValue.FIVE
    }
}
