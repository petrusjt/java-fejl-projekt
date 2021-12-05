package com.epam.training.ticketservice.core.movie.persistence.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovieDtoTest {

    @Test
    void testToMovie() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);

        //when
        final Movie movie = movieDto.toMovie();

        //then
        assertNull(movie.getId());
        assertEquals("title", movie.getTitle());
        assertEquals("genre", movie.getGenre());
        assertEquals(130L, movie.getLength());
    }

    @Test
    void testToString() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);

        //when
        final String s = movieDto.toString();

        //then
        assertEquals("title (genre, 130 minutes)", s);
    }

    @Test
    void testGetTitle() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);

        //when + then
        assertEquals("title", movieDto.getTitle());
    }

    @Test
    void testGetGenre() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);

        //when + then
        assertEquals("genre", movieDto.getGenre());
    }

    @Test
    void testGetLength() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);

        //when + then
        assertEquals(130L, movieDto.getLength());
    }

    @Test
    void testEquals() {
        //given
        final MovieDto movieDto1 = new MovieDto("title", "genre", 130L);
        final MovieDto movieDto2 = new MovieDto("title", "genre", 130L);

        //when + then
        assertTrue(movieDto1.equals(movieDto2));
        assertTrue(movieDto1.equals(movieDto1));
        assertFalse(movieDto1.equals(new ArrayList<MovieDto>()));
    }

    @Test
    void testHashCode() {
        //given
        final MovieDto movieDto1 = new MovieDto("title", "genre", 130L);
        final MovieDto movieDto2 = new MovieDto("title", "genre", 130L);

        //when + then
        assertEquals(movieDto1.hashCode(), movieDto2.hashCode());
    }
}