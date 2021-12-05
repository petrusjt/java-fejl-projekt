package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotCreatableException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningWouldStartInBreakPeriodException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto2;

import java.util.List;

public interface ScreeningService {

    void createScreening(final ScreeningDto screening) throws ScreeningNotCreatableException,
            NoSuchMovieException, NoSuchRoomException;

    void deleteScreening(final ScreeningDto screening) throws NoSuchScreeningException;

    List<ScreeningDto2> listScreenings();
}
