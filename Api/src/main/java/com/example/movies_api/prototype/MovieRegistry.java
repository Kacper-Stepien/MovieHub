package com.example.movies_api.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Rejestr przechowujący prototypy filmów.
 * Umożliwia rejestrację i pobieranie prototypów na podstawie klucza.
 */
public class MovieRegistry {
    
    private Map<String, MoviePrototype> prototypes = new HashMap<>();
    
    /**
     * Rejestruje nowy prototyp pod wskazanym kluczem.
     * @param key Klucz do zarejestrowania prototypu
     * @param prototype Prototyp do zarejestrowania
     */
    public void registerPrototype(String key, MoviePrototype prototype) {
        prototypes.put(key, prototype);
    }
    
    /**
     * Zwraca prototyp o podanym kluczu.
     * @param key Klucz do wyszukania prototypu
     * @return Prototyp filmu lub null, jeśli nie znaleziono
     */
    public MoviePrototype getPrototype(String key) {
        return prototypes.get(key);
    }
}
