package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.ticket.Ticket;
import com.gabriel.ticketing.domain.ticket.TicketRepository;
import com.gabriel.ticketing.domain.ticket.TicketStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketConsumer {

    private final TicketRepository ticketRepository;

    public TicketConsumer(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @KafkaListener(topics = "event-created-topic", groupId = "ticketing-group")
    @Transactional
    public void consumeEventCreated(Event event) {
        System.out.println(">>> Kafka Consumer: Recebido evento '" + event.getName() + "'. Gerando ingressos...");

        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < event.getCapacity(); i++) {
            tickets.add(new Ticket(event, TicketStatus.AVAILABLE));
        }

        ticketRepository.saveAll(tickets);

        System.out.println(">>> Sucesso: " + tickets.size() + " ingressos gerados para o evento ID: " + event.getId());
    }
}