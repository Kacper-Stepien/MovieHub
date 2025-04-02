package com.example.movies_api.repository;

import com.example.movies_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    //user observer pattern 1/3 2/3 [previously not existing query]
    @Query("SELECT u FROM User u JOIN u.subscribedMovies m WHERE m.id = :movieId")
    List<User> findAllSubscribedToMovie(Long movieId);

}

