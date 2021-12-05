package com.epam.training.ticketservice.core.screening.exception;

public class ScreeningWouldStartInBreakPeriodException extends ScreeningNotCreatableException {

    public ScreeningWouldStartInBreakPeriodException() {
        super("This would start in the break period after another screening in this room");
    }

    public ScreeningWouldStartInBreakPeriodException(final String message) {
        super(message);
    }
}
