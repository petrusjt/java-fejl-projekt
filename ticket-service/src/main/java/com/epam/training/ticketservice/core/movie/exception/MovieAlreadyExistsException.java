package com.epam.training.ticketservice.core.movie.exception;

public class MovieAlreadyExistsException extends Exception {

    public MovieAlreadyExistsException(final String message) {
        super(message);
    }
}
