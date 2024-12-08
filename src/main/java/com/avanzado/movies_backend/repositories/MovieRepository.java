package com.avanzado.movies_backend.repositories;

import com.avanzado.movies_backend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
