package com.gabriel.ticketing.domain.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryIT {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldPersistAndLoadEvent() {
        Event event = new Event(
                "Rock in Rio",
                "Rio de Janeiro",
                LocalDateTime.now().plusDays(30),
                100_000
        );

        Event savedEvent = eventRepository.save(event);

        assertThat(savedEvent.getId()).isNotNull();

        Event foundEvent = eventRepository.findById(savedEvent.getId())
                .orElseThrow();

        assertThat(foundEvent.getName()).isEqualTo("Rock in Rio");
        assertThat(foundEvent.getLocation()).isEqualTo("Rio de Janeiro");
        assertThat(foundEvent.getCapacity()).isEqualTo(100_000);
    }
}
