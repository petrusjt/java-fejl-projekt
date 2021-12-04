package com.epam.training.ticketservice.core.screening.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public final class ScreeningDto {

    private final String movieTitle;
    private final String roomName;
    private final LocalDateTime startTime;
}
