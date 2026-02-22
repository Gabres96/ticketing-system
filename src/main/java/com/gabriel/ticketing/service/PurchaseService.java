package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.ticket.TicketRepository;
import com.gabriel.ticketing.domain.ticket.TicketStatus;
import com.gabriel.ticketing.dto.purchase.PurchaseEventDTO;
import com.gabriel.ticketing.dto.purchase.PurchaseRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {


    private final TicketRepository ticketRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PurchaseService(TicketRepository ticketRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.ticketRepository = ticketRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void processPurchase(PurchaseRequestDTO request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var ticket = ticketRepository.findFirstByEventIdAndStatus(request.eventId(), TicketStatus.AVAILABLE)
                .orElseThrow(() -> new RuntimeException("Ingressos esgotados!"));

        ticket.setStatus(TicketStatus.RESERVED);
        ticketRepository.save(ticket);

        kafkaTemplate.send("ticket-purchase-topic", new PurchaseEventDTO(ticket.getId(), userEmail));
    }
}