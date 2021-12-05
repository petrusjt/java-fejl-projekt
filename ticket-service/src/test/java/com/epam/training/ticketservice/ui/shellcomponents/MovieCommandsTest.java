package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto2;
import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.UserService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MovieCommandsTest {

    private MovieCommands underTest;
    private PasswordEncoder passwordEncoder;

    @Mock
    private LoginService loginService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new SCryptPasswordEncoder();
        underTest = new MovieCommands(loginService, movieService);
    }

    @Test
    void createMovieShouldWorkAsIntended() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        
        //when
        final String actual = underTest.createMovie("title", "genre", 130L);

        //then
        assertNull(actual);
    }

    @Test
    void createMovieShouldShouldReturnErrorMessage() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();

        try {
            Mockito.doThrow(new MovieAlreadyExistsException("title")).when(movieService).createMovie(
                    new MovieDto("title", "genre", 130L));
        } catch (MovieAlreadyExistsException e) {
            e.printStackTrace();
        }

        //when
        final String actual = underTest.createMovie("title", "genre", 130L);

        //then
        assertEquals("Movie already exists by title 'title'", actual);
    }

    @Test
    void updateMovie() {
    }

    @Test
    void deleteMovie() {
    }

    @Test
    void listMoviesWithExistingMovies() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        Mockito.doReturn(List.of(new MovieDto("title", "genre", 130L))).when(movieService).listMovies();

        //when
        final List<Object> movies = underTest.listMovies();

        //then
        assertEquals(1, movies.size());
        assertEquals("title (genre, 130 minutes)", movies.get(0).toString());
    }

    @Test
    void listMoviesWithNoExistingMovies() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();
        Mockito.doReturn(Collections.EMPTY_LIST).when(movieService).listMovies();

        //when
        final List<Object> movies = underTest.listMovies();

        //then
        assertEquals(1, movies.size());
        assertEquals("There are no movies at the moment", movies.get(0).toString());
    }
}