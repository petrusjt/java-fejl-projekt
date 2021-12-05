package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotCreatableException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningWouldStartInBreakPeriodException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningServiceImplTest {

    private final static LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2021, 12, 5, 14, 0);

    private ScreeningService underTest;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        final Field field = ScreeningServiceImpl.class.getDeclaredField("breakPeriodLength");
        field.setAccessible(true);
        underTest = new ScreeningServiceImpl(screeningRepository, roomRepository, movieRepository);
        field.set(underTest, Long.valueOf(10L));

    }

    @Test
    void testCreateScreeningShouldWorkAsExpected() {
        //given
        final ScreeningDto screeningDto = new ScreeningDto("title", "name", TEST_DATE_TIME);
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");

        //when
        try {
            underTest.createScreening(screeningDto);
        } catch (final ScreeningNotCreatableException | NoSuchRoomException | NoSuchMovieException e) {
            fail(e);
        }

        //then
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle("title");
        Mockito.verify(roomRepository, Mockito.times(1)).findByName("name");
        Mockito.verify(screeningRepository, Mockito.times(1))
                .existsByMovieTitleAndRoomNameAndStartTime("title", "name", TEST_DATE_TIME);
    }

    @Test
    void testCreateScreeningWithNonExistentMovieShouldThrowNoSuchMovieException() {
        //given
        Mockito.doReturn(Optional.empty()).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");

        //when+then
        assertThrows(NoSuchMovieException.class, () -> underTest.createScreening(new ScreeningDto("title", "name",
                TEST_DATE_TIME)));
    }

    @Test
    void testCreateScreeningWithNonExistentRoomShouldThrowNoSuchRoomException() {
        //given
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.empty()).when(roomRepository).findByName("name");

        //when+then
        assertThrows(NoSuchRoomException.class, () -> underTest.createScreening(new ScreeningDto("title", "name",
                TEST_DATE_TIME)));
    }

    @Test
    void testCreateScreeningShouldThrowScreeningAlreadyExistsException() {
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(true).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");

        //when+then
        assertThrows(ScreeningAlreadyExistsException.class, () -> underTest.createScreening(new ScreeningDto("title", "name",
                TEST_DATE_TIME)));
    }

    @Test
    void testCreateScreeningWithOverlappingScreeningShouldThrowScreeningOverlapsException() {
        //given
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");
        Mockito.doReturn(List.of(new Screening(1L, new Movie(1L, "title", "genre", 123L), new Room(1L, "name",
                20L, 10L), TEST_DATE_TIME))).when(screeningRepository).findAllByRoomName("name");

        //when+then
        assertThrows(ScreeningOverlapsException.class, () -> underTest.createScreening(new ScreeningDto("title", "name",
                TEST_DATE_TIME)));
    }

    @Test
    void testCreateScreeningShouldThrowScreeningWouldStartInBreakPeriodException() {
        //given
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");
        Mockito.doReturn(List.of(new Screening(1L, new Movie(1L, "title", "genre", 130L), new Room(1L, "name",
                20L, 10L), TEST_DATE_TIME))).when(screeningRepository).findAllByRoomName("name");

        //when+then
        assertThrows(ScreeningWouldStartInBreakPeriodException.class,
                () -> underTest.createScreening(new ScreeningDto("title", "name", TEST_DATE_TIME.plusMinutes(135L))));
    }

    @Test
    void testDeleteScreeningShouldWorkAsExpected() {
        //given
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");
        Mockito.doReturn(Optional.of(new Screening(1L, new Movie(1L, "title", "genre", 130L), new Room(1L, "name",
                20L, 10L), TEST_DATE_TIME))).when(screeningRepository).findByMovieTitleAndRoomNameAndStartTime(
                        "title", "name", TEST_DATE_TIME);

        //when+then
        try {
            underTest.deleteScreening(new ScreeningDto("title", "name", TEST_DATE_TIME));
        } catch (final NoSuchScreeningException e) {
            fail(e);
        }
    }

    @Test
    void testDeleteScreeningShouldThrowNoSuchScreeningException() {
        //given
        Mockito.doReturn(Optional.of(new Movie(null, "title", "genre", 130L))).when(movieRepository).findByTitle("title");
        Mockito.doReturn(false).when(screeningRepository).existsByMovieTitleAndRoomNameAndStartTime("title", "name",
                TEST_DATE_TIME);
        Mockito.doReturn(Optional.of(new Room(null, "name", 20L, 10L))).when(roomRepository).findByName("name");
        Mockito.doReturn(Optional.empty()).when(screeningRepository).findByMovieTitleAndRoomNameAndStartTime(
                "title", "name", TEST_DATE_TIME);

        //when+then
        assertThrows(NoSuchScreeningException.class, () ->
                underTest.deleteScreening(new ScreeningDto("title", "name", TEST_DATE_TIME)));
    }
}