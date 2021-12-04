package com.epam.training.ticketservice.core.room.exception;

public class RoomAlreadyExistsException extends Exception {

    public RoomAlreadyExistsException(final String roomName) {
        super(String.format("Room already exists by name '%s'", roomName));
    }
}
