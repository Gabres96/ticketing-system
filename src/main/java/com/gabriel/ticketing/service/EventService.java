package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.event.EventRepository;
import com.gabriel.ticketing.exception.BusinessException;
import com.gabriel.ticketing.exception.EventNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventService(EventRepository eventRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Event createEvent(Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Event date cannot be in the past");
        }

        Event savedEvent = eventRepository.save(event);

        try {
            kafkaTemplate.send("event-created-topic", savedEvent.getId().toString(), savedEvent);
        } catch (Exception e) {
            System.err.println("Erro ao enviar para o Kafka: " + e.getMessage());
        }

        return savedEvent;
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}
