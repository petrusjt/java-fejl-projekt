package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotCreatableException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto2;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningCommandsTest {

    private static final String TEST_DATE_TIME_STRING = "2021-12-05 13:00";
    private static final DateTimeFormatter TEST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.from(
            TEST_DATE_TIME_FORMATTER.parse(TEST_DATE_TIME_STRING));

    private ScreeningCommands underTest;
    private PasswordEncoder passwordEncoder;

    @Mock
    private LoginService loginService;

    @Mock
    private ScreeningService screeningService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new SCryptPasswordEncoder();
        underTest = new ScreeningCommands(loginService, screeningService);
    }

    @Test
    void testCreateScreeningShouldWorkAsExpected() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();


        //when
        final String actual = underTest.createScreening("title", "name", TEST_DATE_TIME_STRING);

        //then
        assertNull(actual);
    }

    @Test
    void testCreateScreeningShouldReturnErrorMessage() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        try {
            Mockito.doThrow(new ScreeningAlreadyExistsException()).when(screeningService).createScreening(
                    new ScreeningDto("title", "name", TEST_DATE_TIME));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        //when
        final String actual = underTest.createScreening("title", "name", TEST_DATE_TIME_STRING);

        //then
        assertEquals("Screening already exists", actual);
    }

    @Test
    void deleteScreeningShouldWorkAsExpected() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();

        //when
        final String actual = underTest.createScreening("title", "name", TEST_DATE_TIME_STRING);

        //then
        assertNull(actual);
    }

    @Test
    void testDeleteScreeningShouldReturnErrorMessage() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        try {
            Mockito.doThrow(new NoSuchScreeningException()).when(screeningService).deleteScreening(
                    new ScreeningDto("title", "name", TEST_DATE_TIME));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        //when
        final String actual = underTest.deleteScreening("title", "name", TEST_DATE_TIME_STRING);

        //then
        assertEquals("No such screening exists", actual);
    }

    @Test
    void listScreeningsWithExistingScreenings() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        Mockito.doReturn(List.of(new ScreeningDto2(
                new MovieDto("title", "genre", 130L),
                new RoomDto("name", 20L, 10L),
                TEST_DATE_TIME))).when(screeningService).listScreenings();

        //when
        final List<Object> screenings = underTest.listScreenings();

        //then
        assertEquals(1, screenings.size());
        assertEquals("title (genre, 130 minutes), screened in room name, at 2021-12-05 13:00",
                screenings.get(0).toString());
    }

    @Test
    void listScreeningsWithNoExistingScreenings() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        Mockito.doReturn(Collections.EMPTY_LIST).when(screeningService).listScreenings();

        //when
        final List<Object> screenings = underTest.listScreenings();

        //then
        assertEquals(1, screenings.size());
        assertEquals("There are no screenings", screenings.get(0).toString());
    }
}