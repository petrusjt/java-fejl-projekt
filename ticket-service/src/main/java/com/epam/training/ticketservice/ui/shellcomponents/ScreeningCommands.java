package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotCreatableException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto2;
import com.epam.training.ticketservice.core.security.LoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class ScreeningCommands extends SecuredCommand {

    private final ScreeningService screeningService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScreeningCommands(final LoginService loginService, final ScreeningService screeningService) {
        super(loginService);
        this.screeningService = screeningService;
    }

    @ShellMethod(value = "Create screening", key = "create screening")
    @ShellMethodAvailability("isAdminUser")
    public String createScreening(final String movieTitle, final String roomName, final String startTime) {
        final LocalDateTime startDateTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(startTime));
        final ScreeningDto screeningDto = new ScreeningDto(movieTitle, roomName, startDateTime);
        try {
            screeningService.createScreening(screeningDto);
            return null;
        } catch (final ScreeningNotCreatableException | NoSuchMovieException | NoSuchRoomException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    @ShellMethodAvailability("isAdminUser")
    public String deleteScreening(final String movieTitle, final String roomName, final String startTime) {
        final LocalDateTime startDateTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(startTime));
        final ScreeningDto screeningDto = new ScreeningDto(movieTitle, roomName, startDateTime);
        try {
            screeningService.deleteScreening(screeningDto);
            return null;
        } catch (final NoSuchScreeningException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public List<Object> listScreenings() {
        final List<ScreeningDto2> screenings = screeningService.listScreenings();
        if (CollectionUtils.isEmpty(screenings)) {
            return List.of("There are no screenings");
        }

        return new ArrayList<>(screenings);
    }
}
