package com.gabriel.ticketing.controller.event;

import com.gabriel.ticketing.dto.event.CreateEventRequest;
import com.gabriel.ticketing.dto.event.EventResponse;
import com.gabriel.ticketing.domain.event.Event;

import com.gabriel.ticketing.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest request) {
        Event event = new Event(
                request.name(),
                request.location(),
                request.eventDate(),
                request.capacity()
        );

        Event savedEvent = eventService.createEvent(event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EventResponse.from(savedEvent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok(EventResponse.from(event));
    }
}
