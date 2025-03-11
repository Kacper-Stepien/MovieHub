package com.example.movies_api.prototype;

/**
 * Interfejs definiujący kontrakt dla prototypów filmów.
 * Rozszerza Cloneable, aby umożliwić klonowanie obiektów.
 */
public interface MoviePrototype extends Cloneable {
    
    /**
     * Metoda do klonowania prototypu.
     * @return Nowa instancja klasy implementującej ten interfejs.
     */
    MoviePrototype clone();
    
    /**
     * Ustawia tytuł filmu.
     * @param title Tytuł filmu
     */
    void setTitle(String title);
    
    /**
     * Zwraca informacje o filmie.
     * @return String z informacjami o filmie
     */
    String getInfo();
}
