package com.avanzado.movies_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avanzado.movies_backend.repositories.MovieRepository;
import com.avanzado.movies_backend.models.Movie;

@RestController
@RequestMapping("/api/movies")                      // localhost:8081/api/movies
public class MovieController {
    
    @Autowired                                      // movieRepository
    private MovieRepository movieRepository;

    // CRUD
    @GetMapping                                     // localhost:8081/api/movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();           // extendido del JPA repository de hibernate
    }

}
