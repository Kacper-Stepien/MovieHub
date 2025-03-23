package com.example.movies_api.flyweight;


// Flyweight 1 /////////////////////////////////////////////////////////////////////////////////////////////////////////
public record MovieType(String type) {

    public static final MovieType FULL_LENGTH = new MovieType("FULL_LENGTH");
    public static final MovieType TRAILER = new MovieType("TRAILER");
    public static final MovieType SERIES = new MovieType("SERIES");

    public static MovieType valueOf(String type) {
        return switch (type) {
            case "FULL_LENGTH" -> FULL_LENGTH;
            case "TRAILER" -> TRAILER;
            case "SERIES" -> SERIES;
            default -> new MovieType(type);
        };
    }

    @Override
    public String toString() {
        return type;
    }
}