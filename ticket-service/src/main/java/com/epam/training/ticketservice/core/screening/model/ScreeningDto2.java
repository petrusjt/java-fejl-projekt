package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public final class ScreeningDto2 {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MovieDto movie;
    private final RoomDto room;
    private final LocalDateTime startTime;

    @Override
    public String toString() {
        return String.format("%s (%s, %d minutes), screened in room %s, at %s",
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
