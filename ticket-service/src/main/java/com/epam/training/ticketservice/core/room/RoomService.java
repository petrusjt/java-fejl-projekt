package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(final RoomDto roomDto) throws RoomAlreadyExistsException;

    void updateRoom(final RoomDto roomDto) throws NoSuchRoomException;

    void deleteRoom(final String name) throws NoSuchRoomException;

    List<RoomDto> listRooms();
}
