package com.example.movies_api;

import com.example.movies_api.prototype.MoviePrototype;
import com.example.movies_api.prototype.MovieRegistry;
import com.example.movies_api.prototype.ActionMovie;
import com.example.movies_api.prototype.ComedyMovie;
import com.example.movies_api.prototype.DocumentaryMovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class MoviesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApiApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demonstratePrototypePattern() {
        return args -> {
            // Inicjalizacja rejestru prototypów
            MovieRegistry registry = new MovieRegistry();
            
            // Rejestracja prototypów
            registry.registerPrototype("action", new ActionMovie("Przykładowy film akcji", 120, 18));
            registry.registerPrototype("comedy", new ComedyMovie("Przykładowa komedia", 95, "komedia romantyczna"));
            registry.registerPrototype("documentary", new DocumentaryMovie("Przykładowy dokument", 110, "przyrodniczy"));
            
            // Tworzenie kopii z prototypów
            MoviePrototype actionMovie1 = registry.getPrototype("action").clone();
            actionMovie1.setTitle("Szybcy i wściekli");
            
            MoviePrototype actionMovie2 = registry.getPrototype("action").clone();
            actionMovie2.setTitle("Mission Impossible");
            
            MoviePrototype comedyMovie = registry.getPrototype("comedy").clone();
            comedyMovie.setTitle("Ted");
            
            MoviePrototype documentaryMovie = registry.getPrototype("documentary").clone();
            documentaryMovie.setTitle("Planet Earth");
            
            // Wyświetlanie informacji o stworzonych filmach
            System.out.println("--- Demonstracja wzorca Prototype ---");
            System.out.println(actionMovie1.getInfo());
            System.out.println(actionMovie2.getInfo());
            System.out.println(comedyMovie.getInfo());
            System.out.println(documentaryMovie.getInfo());
        };
    }
}
