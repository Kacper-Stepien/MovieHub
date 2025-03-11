package com.example.movies_api.prototype;

/**
 * Konkretny prototyp reprezentujący film akcji.
 */
public class ActionMovie implements MoviePrototype {
    
    private String title;
    private int duration;
    private int ageRestriction;
    
    public ActionMovie(String title, int duration, int ageRestriction) {
        this.title = title;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
    }
    
    @Override
    public MoviePrototype clone() {
        try {
            return (ActionMovie) super.clone();
        } catch (CloneNotSupportedException e) {
            return new ActionMovie(this.title, this.duration, this.ageRestriction);
        }
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getInfo() {
        return "Film akcji: " + title + ", czas trwania: " + duration + " min, ograniczenie wiekowe: " + ageRestriction + "+";
    }
}
