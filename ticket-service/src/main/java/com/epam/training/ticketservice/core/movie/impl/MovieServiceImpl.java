package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()))
                .collect(Collectors.toList());
    }

    @Override
    public void createMovie(final MovieDto movieDto) throws MovieAlreadyExistsException {
        final Movie movie = movieDto.toMovie();
        if (movieRepository.existsByTitle(movie.getTitle())) {
            throw new MovieAlreadyExistsException(movieDto.getTitle());
        }
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(final MovieDto movieDto) throws NoSuchMovieException {
        final Movie movie = movieRepository.findByTitle(movieDto.getTitle()).orElseThrow(() ->
                new NoSuchMovieException(movieDto.getTitle()));
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setLength(movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(final String title) throws NoSuchMovieException {
        final Movie movie = movieRepository.findByTitle(title).orElseThrow(() ->
                new NoSuchMovieException(title));
        movieRepository.delete(movie);
    }
}
