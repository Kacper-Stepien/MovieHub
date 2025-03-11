package com.example.movies_api.prototype;

/**
 * Konkretny prototyp reprezentujący film dokumentalny.
 */
public class DocumentaryMovie implements MoviePrototype {
    
    private String title;
    private int duration;
    private String subject;
    
    public DocumentaryMovie(String title, int duration, String subject) {
        this.title = title;
        this.duration = duration;
        this.subject = subject;
    }
    
    @Override
    public MoviePrototype clone() {
        try {
            return (DocumentaryMovie) super.clone();
        } catch (CloneNotSupportedException e) {
            return new DocumentaryMovie(this.title, this.duration, this.subject);
        }
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getInfo() {
        return "Film dokumentalny: " + title + ", czas trwania: " + duration + " min, temat: " + subject;
    }
}
