package com.example.movies_api.proxy;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.movie_data_provider.MovieProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy for movie data that provides caching, logging, and 
 * potential future access control functionality.
 */
@Component
public class MovieDataProxy implements MovieProvider {
    private final MovieProvider localMovieProvider;
    private final MovieProvider externalMovieProvider;
    private final Map<String, List<MovieDto>> movieCache = new HashMap<>();
    private static final long CACHE_EXPIRATION_MS = 300000; // 5 minutes
    private long lastCacheUpdate = 0;
    private String lastSource = null;

    public MovieDataProxy(
            @Qualifier("localMovieProvider") MovieProvider localMovieProvider,
            @Qualifier("externalMovieProvider") MovieProvider externalMovieProvider) {
        this.localMovieProvider = localMovieProvider;
        this.externalMovieProvider = externalMovieProvider;
    }
    
    @Override
    public List<MovieDto> getMovies() {
        System.out.println("MovieDataProxy: Accessing movies with default source");
        return getMoviesFromSource("local");
    }
    
    public List<MovieDto> getMoviesFromSource(String source) {
        System.out.println("MovieDataProxy: Accessing movies from source: " + source);
        return getCachedMovies(source);
    }
    
    private List<MovieDto> getCachedMovies(String source) {
        long currentTime = System.currentTimeMillis();
        
        // If we have cached data that's not expired and for the same source
        if (lastCacheUpdate > 0 && 
            (currentTime - lastCacheUpdate) < CACHE_EXPIRATION_MS &&
            (source == null || source.equals(lastSource)) &&
            movieCache.containsKey(String.valueOf(source))) {
            
            System.out.println("MovieDataProxy: Returning cached movie data");
            return movieCache.get(String.valueOf(source));
        }
        
        // Otherwise fetch fresh data
        System.out.println("MovieDataProxy: Cache miss or expired, fetching fresh data");
        List<MovieDto> movies;
        
        if ("external".equalsIgnoreCase(source)) {
            movies = externalMovieProvider.getMovies();
        } else {
            movies = localMovieProvider.getMovies();
        }
        
        // Update cache
        movieCache.put(String.valueOf(source), movies);
        lastCacheUpdate = currentTime;
        lastSource = source;
        
        return movies;
    }
}
