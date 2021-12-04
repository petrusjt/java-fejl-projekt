package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreeningDto2 {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private MovieDto movie;
    private RoomDto room;
    private LocalDateTime startTime;

    @Override
    public String toString() {
        return String.format("%s (%s, %d), screened in room %s, at %s",
                movie.getTitle(),
                movie.getGenre(),
                movie.getLength(),
                room.getName(),
                getFormattedDate());
    }

    private String getFormattedDate() {
        return startTime.format(DATE_TIME_FORMATTER);
    }
}
