package com.example.movies_api.flyweight;

// Flyweight 2 /////////////////////////////////////////////////////////////////////////////////////////////////////////////
public record RoleName(String name) {
    public static final RoleName AKTOR = new RoleName("AKTOR");
    public static final RoleName REZYSER = new RoleName("REŻYSER");
    public static final RoleName DZWIEKOWIEC = new RoleName("DŹWIĘKOWIEC");

    public static RoleName valueOf(String role) {
        return switch (role) {
            case "AKTOR" -> AKTOR;
            case "REŻYSER" -> REZYSER;
            case "DŹWIĘKOWIEC" -> DZWIEKOWIEC;
            default -> new RoleName(role);
        };
    }

    @Override
    public String toString() {
        return name;
    }
}