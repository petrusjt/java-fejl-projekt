package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(final RoomDto roomDto);

    void updateRoom(final RoomDto roomDto);

    void deleteRoom(final String name);

    List<RoomDto> listRooms();
}
