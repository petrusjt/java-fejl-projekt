package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserService underTest;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new UserServiceImpl(loginService);
    }

    @Test
    void testDescribeAccount() {
        //given
        Mockito.doReturn(new UserDto("admin", Role.ADMIN)).when(loginService).getLoggedInUserData();

        //when
        final UserDto userDto = underTest.describeAccount();

        //then
        assertEquals("admin", userDto.getUsername());
        assertEquals(Role.ADMIN, userDto.getRole());
    }
}