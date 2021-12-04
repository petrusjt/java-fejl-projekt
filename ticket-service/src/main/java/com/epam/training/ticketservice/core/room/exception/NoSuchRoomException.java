package com.epam.training.ticketservice.core.room.exception;

public class NoSuchRoomException extends Exception {

    public NoSuchRoomException(final String roomName) {
        super(String.format("Room already exists by name '%s'", roomName));
    }
}
