package com.gabriel.ticketing.dto.event;

import com.gabriel.ticketing.domain.event.Event;

import java.time.LocalDateTime;

public record EventResponse (
       Long id,
       String name,
       String location,
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
