package com.epam.training.ticketservice.core.security.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(final String message) {
        super(message);
    }
}
