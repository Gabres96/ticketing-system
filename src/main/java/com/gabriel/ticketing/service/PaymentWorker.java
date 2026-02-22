package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.ticket.Ticket;
import com.gabriel.ticketing.domain.ticket.TicketRepository;
import com.gabriel.ticketing.domain.ticket.TicketStatus;
import com.gabriel.ticketing.dto.purchase.PurchaseEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentWorker {
    private static final Logger log = LoggerFactory.getLogger(PaymentWorker.class);
    private final TicketRepository ticketRepository;

    public PaymentWorker(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @KafkaListener(topics = "ticket-purchase-topic", groupId = "payment-group")
    @Transactional
    public void processPayment(PurchaseEventDTO purchaseData) {
        Ticket ticket = ticketRepository.findById(purchaseData.ticketId())
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado: " + purchaseData.ticketId()));

        try {
            Thread.sleep(2000);

            boolean paymentApproved = Math.random() > 0.2;

            if (paymentApproved) {
                ticket.setStatus(TicketStatus.CONFIRMED);
                log.info("Pagamento aprovado para o Ticket {} (Comprador: {})", ticket.getId(), purchaseData.userEmail());
            } else {
                ticket.setStatus(TicketStatus.AVAILABLE);
                log.warn("Pagamento negado para o Ticket {}. Retornando ao status disponível.", ticket.getId());
            }

            ticketRepository.save(ticket);

        } catch (InterruptedException e) {
            log.error("Erro no processamento do pagamento para o ticket {}", purchaseData.ticketId(), e);
            Thread.currentThread().interrupt();
        }
    }
}