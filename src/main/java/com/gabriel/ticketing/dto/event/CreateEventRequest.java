package com.gabriel.ticketing.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(type = "string", example = "2026-12-31T20:00:00")
        LocalDateTime eventDate,

        @Positive
        Integer capacity
) {
}
