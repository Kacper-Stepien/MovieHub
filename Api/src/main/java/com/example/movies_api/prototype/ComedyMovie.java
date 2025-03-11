package com.example.movies_api.prototype;

/**
 * Konkretny prototyp reprezentujący komedię.
 */
public class ComedyMovie implements MoviePrototype {
    
    private String title;
    private int duration;
    private String comedyType;
    
    public ComedyMovie(String title, int duration, String comedyType) {
        this.title = title;
        this.duration = duration;
        this.comedyType = comedyType;
    }
    
    @Override
    public MoviePrototype clone() {
        try {
            return (ComedyMovie) super.clone();
        } catch (CloneNotSupportedException e) {
            return new ComedyMovie(this.title, this.duration, this.comedyType);
        }
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getInfo() {
        return "Komedia: " + title + ", czas trwania: " + duration + " min, typ: " + comedyType;
    }
}
