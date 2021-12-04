package com.epam.training.ticketservice.core.movie.exception;

public class NoSuchMovieException extends Exception {

    public NoSuchMovieException(final String movieTitle) {
        super(String.format("No movie exists with title '%s'", movieTitle));
    }
}
