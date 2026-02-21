package com.gabriel.ticketing.controller.event;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.dto.event.CreateEventRequest;
import com.gabriel.ticketing.dto.event.EventResponse;
import com.gabriel.ticketing.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Events",
        description = "Endpoints responsible for event management"
)
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Create a new event",
            description = "Creates a new event with name, location, date and capacity"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody CreateEventRequest request
    ) {
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

    @Operation(
            summary = "Get event by id",
            description = "Returns event details by its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long id
    ) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok(EventResponse.from(event));
    }
}
