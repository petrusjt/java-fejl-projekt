package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(final RoomDto roomDto) {
        final Room room = roomDto.toRoom();
        roomRepository.save(room);
    }

    /*
    * final Movie movie = movieRepository.findByTitle(movieDto.getTitle());
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setLength(movieDto.getLength());
        movieRepository.save(movie);
    * */
    @Override
    public void updateRoom(final RoomDto roomDto) {
        final Room room = roomRepository.findByName(roomDto.getName());
        room.setName(roomDto.getName());
        room.setNumberOfRows(roomDto.getNumberOfRows());
        room.setNumberOfRows(roomDto.getNumberOfColumns());
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(final String name) {
        final Room room = roomRepository.findByName(name);
        roomRepository.delete(room);
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDto(room.getName(), room.getNumberOfRows(), room.getNumberOfColumns()))
                .collect(Collectors.toList());
    }
}
