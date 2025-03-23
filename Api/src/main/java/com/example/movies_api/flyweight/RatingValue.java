package com.example.movies_api.flyweight;

// Flyweight 3 /////////////////////////////////////////////////////////////////////////////////////////////////////////////
public record RatingValue(int value) {

    public static final RatingValue ONE  = new RatingValue(1);
    public static final RatingValue TWO  = new RatingValue(2);
    public static final RatingValue THREE = new RatingValue(3);
    public static final RatingValue FOUR  = new RatingValue(4);
    public static final RatingValue FIVE  = new RatingValue(5);
    public static final RatingValue SIX   = new RatingValue(6);
    public static final RatingValue SEVEN = new RatingValue(7);
    public static final RatingValue EIGHT = new RatingValue(8);
    public static final RatingValue NINE  = new RatingValue(9);
    public static final RatingValue TEN   = new RatingValue(10);

    public static RatingValue of(int val) {
        return switch (val) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            case 7 -> SEVEN;
            case 8 -> EIGHT;
            case 9 -> NINE;
            case 10 -> TEN;
            default -> new RatingValue(val);
        };
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
