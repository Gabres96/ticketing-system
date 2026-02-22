package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.event.EventRepository;
import com.gabriel.ticketing.domain.ticket.Ticket;
import com.gabriel.ticketing.domain.ticket.TicketRepository;
import com.gabriel.ticketing.domain.ticket.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public EventConsumer(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @KafkaListener(topics = "event-created-topic", groupId = "ticketing-group")
    @Transactional
    public void consume(Event eventData) {
        Event event = eventRepository.findById(eventData.getId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + eventData.getId()));

        log.info("Gerando {} ingressos para o evento: {}", event.getCapacity(), event.getName());

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < event.getCapacity(); i++) {
            tickets.add(new Ticket(event, TicketStatus.AVAILABLE));
        }

        ticketRepository.saveAll(tickets);

        log.info("Ingressos gerados com sucesso para o evento ID {}", event.getId());
    }
}