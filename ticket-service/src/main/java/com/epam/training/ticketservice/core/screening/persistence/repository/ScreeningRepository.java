package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    Optional<Screening> findByMovieTitleAndRoomNameAndStartTime(final String movieTitle,
                                                                final String roomName,
                                                                final LocalDateTime startTime);

    List<Screening> findAllByRoomName(final String roomName);

    boolean existsByMovieTitleAndRoomNameAndStartTime(final String movieTitle,
                                                      final String roomName,
                                                      final LocalDateTime startTime);
}
