package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class RoomServiceImplTest {

    private RoomService underTest;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new RoomServiceImpl(roomRepository);
    }

    @Test
    void testCreateRoomShouldWorkAsExpected() {
        //given
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final Room room = roomDto.toRoom();
        Mockito.doReturn(false).when(roomRepository).existsByName("name");

        //when
        try {
            underTest.createRoom(roomDto);
        } catch (RoomAlreadyExistsException e) {
            fail(e);
        }

        //then
        Mockito.verify(roomRepository, times(1)).existsByName("name");
    }

    @Test
    void testCreateRoomShouldThrowRoomAlreadyExistsException() {
        //given
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final Room room = roomDto.toRoom();
        Mockito.doReturn(true).when(roomRepository).existsByName("name");

        //when
        assertThrows(RoomAlreadyExistsException.class, () -> underTest.createRoom(roomDto));

        //then
        Mockito.verify(roomRepository, times(1)).existsByName("name");
    }

    @Test
    void testUpdateRoomShouldWorkAsExpected() {
        //given
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final Room room = roomDto.toRoom();
        Mockito.doReturn(Optional.of(new Room(1L, "name", 10L, 20L))).when(roomRepository).findByName("name");

        //when
        try {
            underTest.updateRoom(roomDto);
        } catch (final NoSuchRoomException e) {
            fail(e);
        }

        //then
        Mockito.verify(roomRepository, times(1)).findByName("name");
    }

    @Test
    void testUpdateRoomShouldThrowNoSuchRoomException() {
        //given
        final RoomDto roomDto = new RoomDto("name", 10L, 20L);
        final Room room = roomDto.toRoom();
        Mockito.doReturn(Optional.empty()).when(roomRepository).findByName("name");

        //when
        assertThrows(NoSuchRoomException.class, () -> underTest.updateRoom(roomDto));

        //then
        Mockito.verify(roomRepository, times(1)).findByName("name");
    }

    @Test
    void testDeleteRoomShouldWorkAsExpected() {
        //given
        final String roomName = "name";
        Mockito.doReturn(Optional.of(new Room(1L, "name", 10L, 20L))).when(roomRepository).findByName("name");

        //when
        try {
            underTest.deleteRoom("name");
        } catch (final NoSuchRoomException e) {
            fail(e);
        }

        //then
        Mockito.verify(roomRepository, times(1)).findByName("name");
    }

    @Test
    void testDeleteRoomShouldThrowNoSuchRoomException() {
        //given
        final String roomName = "name";
        Mockito.doReturn(Optional.empty()).when(roomRepository).findByName("name");

        //when
        assertThrows(NoSuchRoomException.class, () -> underTest.deleteRoom("name"));

        //then
        Mockito.verify(roomRepository, times(1)).findByName("name");
    }

    @Test
    void testListMoviesWhenThereAreMovies() {
        //given
        final Room room1 = new Room(1L, "name1", 10L, 20L);
        final Room room2 = new Room(2L, "name2", 10L, 15L);
        Mockito.doReturn(List.of(room1, room2)).when(roomRepository).findAll();

        //when
        final List<RoomDto> rooms = underTest.listRooms();

        //then
        assertEquals(2, rooms.size());
        assertEquals("Room name1 with 200 seats, 10 rows and 20 columns", rooms.get(0).toString());
    }

    @Test
    void testListMoviesWhenThereAreNoMovies() {
        //given
        Mockito.doReturn(List.of()).when(roomRepository).findAll();

        //when
        final List<RoomDto> movies = underTest.listRooms();

        //then
        assertEquals(0, movies.size());
    }
}