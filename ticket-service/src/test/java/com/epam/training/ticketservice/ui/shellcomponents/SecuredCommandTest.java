package com.epam.training.ticketservice.ui.shellcomponents;

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
import org.springframework.shell.Availability;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SecuredCommandTest {

    private SecuredCommand underTest;
    private PasswordEncoder passwordEncoder;

    @Mock
    private LoginService loginService;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new SCryptPasswordEncoder();
        underTest = new UserCommands(loginService, userService);
    }

    @Test
    void isUserLoggedInShouldWorkAsExpected() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isUserLoggedIn();

        //then
        assertTrue(availability.isAvailable());
    }

    @Test
    void isUserLoggedInWithNoLoggedInUser() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(null).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isUserLoggedIn();

        //then
        assertFalse(availability.isAvailable());
    }

    @Test
    void isUserLoggedInWithLoggedInUserWithNullRole() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", null)).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isUserLoggedIn();

        //then
        assertFalse(availability.isAvailable());
    }

    @Test
    void isAdminUserShouldWorkAsExpected() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isAdminUser();

        //then
        assertTrue(availability.isAvailable());
    }

    @Test
    void isAdminUserWithNoLoggedInUser() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(null).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isAdminUser();

        //then
        assertFalse(availability.isAvailable());
    }

    @Test
    void isAdminUserWithLoggedInUserWithNullRole() {
        //given
        Mockito.doReturn(Optional.of(new User(1L, "admin", passwordEncoder.encode("admin"), Role.ADMIN)))
                .when(userRepository).findByUsername("admin");
        Mockito.doReturn(new UserDto("admin", null)).when(loginService).getLoggedInUserData();

        //when
        final Availability availability = underTest.isAdminUser();

        //then
        assertFalse(availability.isAvailable());
    }
}