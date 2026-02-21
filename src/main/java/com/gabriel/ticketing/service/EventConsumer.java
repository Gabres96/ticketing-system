package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = "event-created-topic", groupId = "ticketing-group")
    public void consume(Event event) {
        log.info("#### Mensagem consumida do Kafka ####");
        log.info("Evento Recebido: ID={}, Nome={}, Local={}",
                event.getId(), event.getName(), event.getLocation());


    }
}
