package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.security.LoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommands extends SecuredCommand {

    private final MovieService movieService;

    public MovieCommands(final LoginService loginService, final MovieService movieService) {
        super(loginService);
        this.movieService = movieService;
    }

    @ShellMethod(value = "Create movie", key = "create movie")
    @ShellMethodAvailability("isAdminUser")
    public void createMovie(final String title, final String genre, final Long length) {
        movieService.createMovie(new MovieDto(title, genre, length));
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    @ShellMethodAvailability("isAdminUser")
    public void updateMovie(final String title, final String genre, final Long length) {
        movieService.updateMovie(new MovieDto(title, genre, length));
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public List<Object> listMovies() {
        //TODO use MovieDto
        final List<Movie> movies = movieService.listMovies();
        if (CollectionUtils.isEmpty(movies)) {
            return List.of("There are no movies at the moment");
        }
        return movies.stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()))
                .collect(Collectors.toList());
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    public void deleteMovie(final String title) {
        movieService.deleteMovie(title);
    }
}
