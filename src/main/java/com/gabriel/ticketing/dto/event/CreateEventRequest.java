package com.gabriel.ticketing.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank(message = "Event name must not be blank")
        String name,
        @NotBlank
        String location,
        @Future
        LocalDateTime eventDate,
        @Positive
        Integer capacity
) {
}
