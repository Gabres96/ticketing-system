package com.gabriel.ticketing.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank
        String name,
        @NotBlank
        String location,
        @Future
        LocalDateTime eventDate,
        @Positive
        int capacity
) {
}
