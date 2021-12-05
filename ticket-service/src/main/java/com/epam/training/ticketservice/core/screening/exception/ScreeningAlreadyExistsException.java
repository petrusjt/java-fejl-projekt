package com.epam.training.ticketservice.core.screening.exception;

public class ScreeningAlreadyExistsException extends ScreeningNotCreatableException {

    public ScreeningAlreadyExistsException() {
        super("Screening already exists");
    }

    public ScreeningAlreadyExistsException(final String message) {
        super(message);
    }
}
