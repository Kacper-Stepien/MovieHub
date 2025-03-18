package com.example.movies_api.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewItemRepository extends JpaRepository<CrewItem, Long> {

}
