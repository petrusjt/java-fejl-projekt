package com.epam.training.ticketservice.core.screening.exception;

public class ScreeningAlreadyExistsException extends Exception{

    public ScreeningAlreadyExistsException(final String message) {
        super(message);
    }
}
