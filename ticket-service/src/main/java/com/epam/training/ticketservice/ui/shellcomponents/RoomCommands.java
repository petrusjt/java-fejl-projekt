package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.security.LoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommands extends SecuredCommand {

    private final RoomService roomService;

    public RoomCommands(final LoginService loginService, final RoomService roomService) {
        super(loginService);
        this.roomService = roomService;
    }

    @ShellMethod(value = "Create room", key = "create room")
    @ShellMethodAvailability("isAdminUser")
    public void createRoom(final String title, final Long numberOfRows, final Long numberOfColumns) {
        final RoomDto roomDto = new RoomDto(title, numberOfRows, numberOfColumns);
        roomService.createRoom(roomDto);
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public List<Object> listRooms() {
        final List<RoomDto> rooms = roomService.listRooms();
        if (CollectionUtils.isEmpty(rooms)) {
            return List.of("There are no rooms at the moment");
        }

        return new ArrayList<>(rooms);
    }

    @ShellMethod(value = "Update room", key = "update room")
    public void updateRoom(final String name, final Long numberOfRows, final Long numberOfColums) {
        final RoomDto roomDto = new RoomDto(name, numberOfRows, numberOfColums);
        roomService.updateRoom(roomDto);
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    public void deleteRoom(final String name) {
        roomService.deleteRoom(name);
    }
}
