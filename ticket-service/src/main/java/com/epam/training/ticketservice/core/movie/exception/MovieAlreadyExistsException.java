package com.epam.training.ticketservice.core.movie.exception;

public class MovieAlreadyExistsException extends Exception {

    public MovieAlreadyExistsException(final String movieTitle) {
        super(String.format("Movie already exists by title '%s'", movieTitle));
    }
}
