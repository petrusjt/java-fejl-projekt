package com.epam.training.ticketservice.core.screening.exception;

public class ScreeningOverlapsException extends ScreeningNotCreatableException {

    public ScreeningOverlapsException() {
        super("There is an overlapping screening");
    }

    public ScreeningOverlapsException(final String message) {
        super(message);
    }
}
