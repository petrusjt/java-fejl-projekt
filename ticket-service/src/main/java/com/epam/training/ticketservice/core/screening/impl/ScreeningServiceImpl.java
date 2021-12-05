package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.exception.NoSuchMovieException;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.exception.NoSuchRoomException;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.NoSuchScreeningException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningNotCreatableException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningWouldStartInBreakPeriodException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto2;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    @Value("${ticketservice.breakperiod.length}")
    private Long breakPeriodLength;

    public ScreeningServiceImpl(final ScreeningRepository screeningRepository,
                                final RoomRepository roomRepository,
                                final MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void createScreening(final ScreeningDto screening)
            throws ScreeningNotCreatableException, NoSuchMovieException, NoSuchRoomException {
        if (screeningRepository.existsByMovieTitleAndRoomNameAndStartTime(screening.getMovieTitle(),
                screening.getRoomName(), screening.getStartTime())) {
            throw new ScreeningAlreadyExistsException();
        }

        final List<Screening> screenings = screeningRepository.findAllByRoomName(screening.getRoomName());
        createScreening(screening, screenings);
    }

    private void createScreening(final ScreeningDto screeningDto, List<Screening> screenings)
            throws ScreeningNotCreatableException, NoSuchMovieException, NoSuchRoomException {
        if (wouldScreeningOverlap(screeningDto, screenings)) {
            throw new ScreeningOverlapsException();
        } else if (wouldScreeningStartInBreakPeriod(screeningDto, screenings)) {
            throw new ScreeningWouldStartInBreakPeriodException();
        }

        final Movie movie = movieRepository.findByTitle(screeningDto.getMovieTitle()).orElseThrow(() ->
                new NoSuchMovieException(screeningDto.getMovieTitle()));
        final Room room = roomRepository.findByName(screeningDto.getRoomName()).orElseThrow(() ->
                new NoSuchRoomException(screeningDto.getRoomName()));
        final Screening screening = new Screening(null, movie, room, screeningDto.getStartTime());
        screeningRepository.save(screening);
    }

    private boolean wouldScreeningOverlap(final ScreeningDto screeningDto, final List<Screening> screenings) {
        return screenings.stream()
                .map(screening -> Pair.of(screening.getStartTime(),
                        screening.getStartTime().plusMinutes(screening.getMovie().getLength())))
                .anyMatch(pair -> isDateBetween(screeningDto.getStartTime(), pair.getLeft(), pair.getRight()));
    }

    private boolean wouldScreeningStartInBreakPeriod(final ScreeningDto screeningDto,
                                                     final List<Screening> screenings) {
        return screenings.stream()
                .map(screening -> Pair.of(screening.getStartTime(),
                        screening.getStartTime().plusMinutes(screening.getMovie().getLength() + breakPeriodLength)))
                .anyMatch(pair -> isDateBetween(screeningDto.getStartTime(), pair.getLeft(), pair.getRight()));
    }

    private boolean isDateBetween(final LocalDateTime value, final LocalDateTime date1, final LocalDateTime date2) {
        return (value.equals(date1) || value.isAfter(date1)) && (value.equals(date2) || value.isBefore(date2));
    }

    @Override
    public void deleteScreening(final ScreeningDto screeningDto) throws NoSuchScreeningException {
        final Screening screening = screeningRepository.findByMovieTitleAndRoomNameAndStartTime(
                screeningDto.getMovieTitle(), screeningDto.getRoomName(), screeningDto.getStartTime())
                .orElseThrow(NoSuchScreeningException::new);

        screeningRepository.delete(screening);
    }

    @Override
    public List<ScreeningDto2> listScreenings() {
        return screeningRepository.findAll().stream()
                .map(screening -> {
                    final Movie movie = screening.getMovie();
                    final Room room = screening.getRoom();

                    return new ScreeningDto2(
                            new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()),
                            new RoomDto(room.getName(), room.getNumberOfRows(), room.getNumberOfColumns()),
                            screening.getStartTime());
                })
                .collect(Collectors.toList());
    }
}
