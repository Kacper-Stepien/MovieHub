package com.example.movies_api.functional;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Examples of using functional programming for processing collections with streams
 */
public class StreamProcessingExamples {

    /**
     * Example 1: Processing a list of movies - filtering, mapping, sorting, and collecting results
     * @param movies list of movies to process
     * @return sorted list of movie titles from a specific year
     */
    public static List<String> processMoviesList(List<Movie> movies, int year) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() != null && movie.getReleaseYear() == year)
                .filter(movie -> movie.getGenre() != null)
                .sorted(Comparator.comparing(Movie::getTitle))
                .map(Movie::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Example 2: Processing a map of statistics - grouping, transformation and data aggregation
     * @param genreRatingsMap map containing movie ratings by genre
     * @return new map with average ratings for each genre
     */
    public static Map<String, Double> processRatingsMap(Map<String, List<Double>> genreRatingsMap) {
        return genreRatingsMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0)
                ));
    }

    /**
     * Example 3: Processing a set of genres - flattening hierarchy, transformation and filtering
     * @param genres set of movie genres (possibly with subgenres)
     * @return flat list of names of all genres and subgenres
     */
    public static Set<String> processGenresSet(Set<Genre> genres) {
        return genres.stream()
                // Flatten the hierarchy - from each genre create a stream containing this genre and all its subgenres
                .flatMap(genre -> Stream.concat(
                        Stream.of(genre), 
                        getAllSubgenres(genre).stream()
                ))
                // Remove duplicates by converting to Set
                .filter(genre -> genre.getName() != null)
                .map(Genre::getName)
                .filter(name -> name.length() > 2)  // Filter out short names
                .map(String::toLowerCase)  // Transform - all lowercase
                .collect(Collectors.toSet());  // Collect results, automatically removing duplicates
    }

    /**
     * Helper method for recursively retrieving all subgenres
     */
    private static List<Genre> getAllSubgenres(Genre genre) {
        List<Genre> result = new ArrayList<>();
        if (genre.getChildren() != null) {
            for (Genre child : genre.getChildren()) {
                result.add(child);
                result.addAll(getAllSubgenres(child));
            }
        }
        return result;
    }

    /**
     * Example of using various stream operations in practical scenarios
     */
    public static void demonstrateStreamOperations() {
        // Sample data
        List<MovieDto> movies = generateSampleMovies();
        
        // 1. Filtering and mapping
        List<String> titles2020 = movies.stream()
                .filter(m -> m.getReleaseYear() == 2020)
                .map(MovieDto::getTitle)
                .collect(Collectors.toList());
        System.out.println("Movies from 2020: " + titles2020);
        
        // 2. Aggregation - finding average rating
        double avgRating = movies.stream()
                .mapToDouble(MovieDto::getAvgRating)
                .average()
                .orElse(0.0);
        System.out.println("Average rating of all movies: " + avgRating);
        
        // 3. Grouping by year
        Map<Integer, List<MovieDto>> moviesByYear = movies.stream()
                .collect(Collectors.groupingBy(MovieDto::getReleaseYear));
        System.out.println("Number of movies by year: " + 
                moviesByYear.entrySet().stream()
                        .map(e -> e.getKey() + ": " + e.getValue().size())
                        .collect(Collectors.joining(", ")));
        
        // 4. Sorting and limit
        List<MovieDto> top3Rated = movies.stream()
                .sorted(Comparator.comparingDouble(MovieDto::getAvgRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 rated movies: " + 
                top3Rated.stream().map(MovieDto::getTitle).collect(Collectors.joining(", ")));
                
        // 5. Using reduce to find the highest rated movie
        Optional<MovieDto> highestRated = movies.stream()
                .reduce((m1, m2) -> m1.getAvgRating() > m2.getAvgRating() ? m1 : m2);
        highestRated.ifPresent(m -> System.out.println("Highest rated movie: " + m.getTitle()));
        
        // 6. Partitioning - dividing into promoted and non-promoted
        Map<Boolean, List<MovieDto>> partitioned = movies.stream()
                .collect(Collectors.partitioningBy(MovieDto::isPromoted));
        System.out.println("Number of promoted movies: " + partitioned.get(true).size());
        System.out.println("Number of non-promoted movies: " + partitioned.get(false).size());
    }
    
    private static List<MovieDto> generateSampleMovies() {
        List<MovieDto> movies = new ArrayList<>();
        
        MovieDto movie1 = new MovieDto();
        movie1.setId(1L);
        movie1.setTitle("Tenet");
        movie1.setReleaseYear(2020);
        movie1.setAvgRating(4.3);
        movie1.setPromoted(true);
        movies.add(movie1);
        
        MovieDto movie2 = new MovieDto();
        movie2.setId(2L);
        movie2.setTitle("Parasite");
        movie2.setReleaseYear(2019);
        movie2.setAvgRating(4.8);
        movie2.setPromoted(true);
        movies.add(movie2);
        
        MovieDto movie3 = new MovieDto();
        movie3.setId(3L);
        movie3.setTitle("Joker");
        movie3.setReleaseYear(2019);
        movie3.setAvgRating(4.5);
        movie3.setPromoted(false);
        movies.add(movie3);
        
        MovieDto movie4 = new MovieDto();
        movie4.setId(4L);
        movie4.setTitle("Wonder Woman 1984");
        movie4.setReleaseYear(2020);
        movie4.setAvgRating(3.7);
        movie4.setPromoted(true);
        movies.add(movie4);
        
        MovieDto movie5 = new MovieDto();
        movie5.setId(5L);
        movie5.setTitle("Soul");
        movie5.setReleaseYear(2020);
        movie5.setAvgRating(4.6);
        movie5.setPromoted(false);
        movies.add(movie5);
        
        return movies;
    }
}
