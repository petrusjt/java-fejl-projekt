package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.security.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningCommandsTest {

    private ScreeningCommands underTest;

    @Mock
    private LoginService loginService;

    @Mock
    private ScreeningService screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new ScreeningCommands(loginService, screeningService);
    }

    @Test
    void createScreening() {
        
    }

    @Test
    void deleteScreening() {
    }

    @Test
    void listScreenings() {
    }
}