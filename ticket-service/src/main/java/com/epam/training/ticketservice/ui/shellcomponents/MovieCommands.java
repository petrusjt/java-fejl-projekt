package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.security.LoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class MovieCommands extends SecuredCommand {

    private final MovieService movieService;

    public MovieCommands(final LoginService loginService, final MovieService movieService) {
        super(loginService);
        this.movieService = movieService;
    }

    @ShellMethod(value = "Create movie", key = "create movie")
    @ShellMethodAvailability("isAdminUser")
    public String createMovie(final String title, final String genre, final Long length) {
        try {
            movieService.createMovie(new MovieDto(title, genre, length));
            return null;
        } catch (MovieAlreadyExistsException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    @ShellMethodAvailability("isAdminUser")
    public String updateMovie(final String title, final String genre, final Long length) {
        try {
            movieService.updateMovie(new MovieDto(title, genre, length));
            return null;
        } catch (NoSuchMovieException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public List<Object> listMovies() {
        final List<MovieDto> movies = movieService.listMovies();
        if (CollectionUtils.isEmpty(movies)) {
            return List.of("There are no movies at the moment");
        }
        return new ArrayList<>(movies);
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    @ShellMethodAvailability("isAdminUser")
    public String deleteMovie(final String title) {
        try {
            movieService.deleteMovie(title);
            return null;
        } catch (NoSuchMovieException e) {
            return e.getMessage();
        }
    }
}
