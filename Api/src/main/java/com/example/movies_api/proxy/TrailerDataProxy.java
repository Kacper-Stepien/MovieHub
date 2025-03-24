package com.example.movies_api.proxy;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.trailer_data_provider.TrailerProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy for trailer data that implements caching to improve performance
 * and provides additional logging functionality.
 */
@Component
public class TrailerDataProxy implements TrailerProvider {
    private final TrailerProvider localTrailerProvider;
    private final TrailerProvider externalTrailerProvider;
    private final Map<String, List<TrailerDto>> trailerCache = new HashMap<>();
    private static final long CACHE_EXPIRATION_MS = 300000; // 5 minutes
    private long lastCacheUpdate = 0;
    private String lastSource = null;

    public TrailerDataProxy(
            @Qualifier("localTrailerProvider") TrailerProvider localTrailerProvider,
            @Qualifier("externalTrailerProvider") TrailerProvider externalTrailerProvider) {
        this.localTrailerProvider = localTrailerProvider;
        this.externalTrailerProvider = externalTrailerProvider;
    }
    
    @Override
    public List<TrailerDto> getTrailers() {
        System.out.println("TrailerDataProxy: Accessing trailers with default source");
        return getTrailersFromSource("local");
    }
    
    public List<TrailerDto> getTrailersFromSource(String source) {
        System.out.println("TrailerDataProxy: Accessing trailers from source: " + source);
        return getCachedTrailers(source);
    }
    
    private List<TrailerDto> getCachedTrailers(String source) {
        long currentTime = System.currentTimeMillis();
        
        // If we have cached data that's not expired and for the same source
        if (lastCacheUpdate > 0 && 
            (currentTime - lastCacheUpdate) < CACHE_EXPIRATION_MS &&
            (source == null || source.equals(lastSource)) &&
            trailerCache.containsKey(String.valueOf(source))) {
            
            System.out.println("TrailerDataProxy: Returning cached trailers data");
            return trailerCache.get(String.valueOf(source));
        }
        
        // Otherwise fetch fresh data
        System.out.println("TrailerDataProxy: Cache miss or expired, fetching fresh data");
        List<TrailerDto> trailers;
        
        if ("external".equalsIgnoreCase(source)) {
            trailers = externalTrailerProvider.getTrailers();
        } else {
            trailers = localTrailerProvider.getTrailers();
        }
        
        // Update cache
        trailerCache.put(String.valueOf(source), trailers);
        lastCacheUpdate = currentTime;
        lastSource = source;
        
        return trailers;
    }
}
