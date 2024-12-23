package com.avanzado.movies_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @CrossOrigin
    @GetMapping("/all")                                     // localhost:8081/api/movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();           // extendido del JPA repository de hibernate
    }

    @CrossOrigin
    @GetMapping("/all/{id}")                     // localhost:8081/api/movies/detail/1
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {        //@PathVariable indica que recibe por parametro
        
        Optional<Movie> movie = movieRepository.findById( id );
    
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin                                            // front pueda realizar peticiones
    @PostMapping                                            // localhost:8081/api/movies
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie ) {

        Movie savedMovie = movieRepository.save( movie );

        return ResponseEntity.status(HttpStatus.CREATED).body( savedMovie );
    }

    @CrossOrigin
    @DeleteMapping("/detail/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id ) {
        // si no existe
        if(!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);

        return ResponseEntity.noContent().build();

    } 

    @CrossOrigin
    @PutMapping("/detail/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody  Movie updatedMovie) {
        
        if(!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // agregamos el id en el movie
        updatedMovie.setId(id);
        
        Movie savedMovie =  movieRepository.save(updatedMovie);

        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @PutMapping("/vote/{id}/rating/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable Long id, @PathVariable double rating) {

        if(!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //generar una nueva pelicula
                    //Movie movie = movieRepository.findById(id);   metodo estatico
        //Optional <Movie> optional = movieRepository.findById(id);
        Movie movie = movieRepository.findById(id).get();

        // calcular el nuevo rating con la media de puntuacion
        // movie.rating
        // movie.votes
        // ((votes * rating) + rating ) / ( votos + 1)
        double newRating = ( (movie.getVotos() * movie.getRating() ) + rating ) / (movie.getVotos() + 1 );

        movie.setVotos( movie.getVotos() + 1 );

        movie.setRating( newRating );

        Movie savedMovie = movieRepository.save( movie );

        return ResponseEntity.ok(savedMovie);

    }

}
