package com.gabriel.ticketing.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabriel.ticketing.domain.event.Event;
import java.time.LocalDateTime;

public record EventResponse (
        Long id,
        String name,
        String location,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime eventDate,
        int capacity
){
    public static EventResponse from (Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getEventDate(),
                event.getCapacity()
        );
    }
}