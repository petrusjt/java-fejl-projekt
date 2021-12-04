package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;

import java.util.List;

public interface MovieService {

    List<MovieDto> listMovies();

    void createMovie(final MovieDto movieDto) throws MovieAlreadyExistsException;

    void updateMovie(final MovieDto movieDto) throws NoSuchMovieException;

    void deleteMovie(final String title) throws NoSuchMovieException;
}
