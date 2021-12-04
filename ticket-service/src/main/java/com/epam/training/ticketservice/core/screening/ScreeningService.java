package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

public interface ScreeningService {

    void createScreening(ScreeningDto screening) throws ScreeningAlreadyExistsException;

    void deleteScreening(ScreeningDto screening) throws NoSuchScreeningException;
}
