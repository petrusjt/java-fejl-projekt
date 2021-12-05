package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MovieServiceImplTest {

    private MovieService underTest;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    void testCreateMovieShouldWorkAsExpected() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final Movie movie = movieDto.toMovie();
        Mockito.doReturn(false).when(movieRepository).existsByTitle("title");

        //when
        try {
            underTest.createMovie(movieDto);
        } catch (final MovieAlreadyExistsException e) {
            fail(e);
        }

        //then
        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void testCreateMovieShouldThrowMovieAlreadyExistsException() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final Movie movie = movieDto.toMovie();
        Mockito.doReturn(true).when(movieRepository).existsByTitle("title");

        //when
        assertThrows(MovieAlreadyExistsException.class, () -> underTest.createMovie(movieDto));

        //then
        verify(movieRepository, times(0)).save(movie);
    }

    @Test
    void testUpdateMovieShouldWorkAsExpected() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final Movie movie = movieDto.toMovie();
        movie.setId(1L);
        Mockito.doReturn(new Movie(1L, "title", "genre", 130L)).when(movieRepository).save(movie);
        Mockito.doReturn(Optional.of(new Movie(1L, "title", "horror", 130L)))
                .when(movieRepository).findByTitle("title");

        //when
        try {
            underTest.updateMovie(movieDto);
        } catch (NoSuchMovieException e) {
            fail(e);
        }

        //then
        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    void testUpdateMovieShouldThrowNoSuchMovieException() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final Movie movie = movieDto.toMovie();
        Mockito.doReturn(Optional.empty()).when(movieRepository).findByTitle("title");

        //when
        assertThrows(NoSuchMovieException.class, () -> underTest.updateMovie(movieDto));

        //then
        Mockito.verify(movieRepository, Mockito.times(0)).save(movie);
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle("title");
    }

    @Test
    void testDeleteMovieShouldWorkAsExpected() {
        final String title = "title";
        Mockito.doReturn(Optional.of(new Movie(1L, "title", "horror", 130L)))
                .when(movieRepository).findByTitle("title");

        //when
        try {
            underTest.deleteMovie(title);
        } catch (NoSuchMovieException e) {
            fail(e);
        }

        //then
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle(title);
    }

    @Test
    void testDeleteMovieShouldThrowNoSuchMovieException() {
        final String title = "title";
        Mockito.doReturn(Optional.empty()).when(movieRepository).findByTitle("title");

        //when
        assertThrows(NoSuchMovieException.class, () -> underTest.deleteMovie("title"));

        //then
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle(title);
    }

    @Test
    void testListMoviesWhenThereAreMovies() {
        //given
        final Movie movie1 = new Movie(1L, "title1", "genre1", 100L);
        final Movie movie2 = new Movie(2L, "title2", "genre2", 200L);
        Mockito.doReturn(List.of(movie1, movie2)).when(movieRepository).findAll();

        //when
        final List<Object> movies = new ArrayList<>(underTest.listMovies());

        //then
        assertEquals(2, movies.size());
        assertEquals(MovieDto.class, movies.get(0).getClass());
        assertEquals(MovieDto.class, movies.get(1).getClass());
        assertEquals("title1 (genre1, 100 minutes)", movies.get(0).toString());
    }

    @Test
    void testListMoviesWhenThereAreNoMovies() {
        //given
        final Movie movie1 = new Movie(1L, "title1", "genre1", 100L);
        final Movie movie2 = new Movie(2L, "title2", "genre2", 200L);
        Mockito.doReturn(List.of()).when(movieRepository).findAll();

        //when
        final List<Object> movies = new ArrayList<>(underTest.listMovies());

        //then
        assertEquals(0, movies.size());
    }
}