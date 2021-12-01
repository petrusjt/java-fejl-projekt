package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;

import java.util.List;

public interface MovieService {

    List<Movie> listMovies();

    void createMovie(final MovieDto movieDto);

    void updateMovie(final MovieDto movieDto);

    void deleteMovie(final String title);
}