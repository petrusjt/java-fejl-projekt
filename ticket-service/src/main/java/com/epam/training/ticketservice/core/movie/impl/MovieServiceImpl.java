package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void createMovie(final MovieDto movieDto) {
        final Movie movie = movieDto.toMovie();
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(final MovieDto movieDto) {
        final Movie movie = movieRepository.findByTitle(movieDto.getTitle());
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setLength(movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String title) {
        final Movie movie = movieRepository.findByTitle(title);
        movieRepository.delete(movie);
    }
}
