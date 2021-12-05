package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDto2Test {

    @Test
    void testToString() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final ScreeningDto2 screeningDto2 = new ScreeningDto2(movieDto, roomDto, LocalDateTime.of(2021, 12, 5, 14, 0));

        //when + then
        assertEquals("title (genre, 130 minutes), screened in room name, at 2021-12-05 14:00",
                screeningDto2.toString());
    }

    @Test
    void testEquals() {
        //given
        final MovieDto movieDto = new MovieDto("title", "genre", 130L);
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final ScreeningDto2 screeningDto2 = new ScreeningDto2(movieDto, roomDto, LocalDateTime.of(2021, 12, 5, 14, 0));
        final ScreeningDto2 screeningDto21 = new ScreeningDto2(movieDto, roomDto, LocalDateTime.of(2021, 12, 5, 14, 0));
        final ScreeningDto2 screeningDto22 = new ScreeningDto2(null, null, null);

        //when + then
        assertTrue(screeningDto2.equals(screeningDto21));
        assertTrue(screeningDto2.equals(screeningDto2));
        assertFalse(screeningDto2.equals(null));
        assertFalse(screeningDto2.equals(screeningDto22));
        assertFalse(screeningDto22.equals(screeningDto2));
        assertFalse(screeningDto2.equals(Long.valueOf(240l)));
    }

    @Test
    void testGetMovie() {
    }

    @Test
    void testGetRoom() {
    }

    @Test
    void testGetStartTime() {
    }
}