package com.epam.training.ticketservice.core.room.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoomDto {

    private final String name;
    private final Long numberOfRows;
    private final Long numberOfColumns;

    private Long getNumberOfSeats() {
        return numberOfRows * numberOfColumns;
    }

    @Override
    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns",
                name,
                getNumberOfSeats(),
                numberOfRows,
                numberOfColumns);
    }

    public Room toRoom() {
        return new Room(null, name, numberOfRows, numberOfColumns);
    }
}
