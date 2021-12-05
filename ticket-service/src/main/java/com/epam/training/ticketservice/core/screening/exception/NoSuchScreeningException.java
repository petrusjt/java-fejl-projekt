package com.epam.training.ticketservice.core.screening.exception;

public class NoSuchScreeningException extends Exception {

    public NoSuchScreeningException() {
        super("No such screening exists");
    }
}
