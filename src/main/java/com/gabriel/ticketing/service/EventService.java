package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.event.EventRepository;
import com.gabriel.ticketing.exception.EventNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}
