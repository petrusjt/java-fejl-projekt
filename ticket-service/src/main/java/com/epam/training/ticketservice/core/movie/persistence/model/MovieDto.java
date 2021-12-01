package com.epam.training.ticketservice.core.movie.persistence.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class MovieDto {

    private final String title;
    private final String genre;
    private final Long length;

    public String toString() {
        return String.format("%s (%s, %d minutes)", title, genre, length);
    }

    public Movie toMovie() {
        return new Movie(null, title, genre, length);
    }
}
