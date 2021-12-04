package com.epam.training.ticketservice.core.movie.exception;

public class NoSuchMovieException extends Exception {

    public NoSuchMovieException(final String message) {
        super(message);
    }
}
