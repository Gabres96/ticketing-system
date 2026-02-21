package com.gabriel.ticketing.service;

import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.event.EventRepository;
import com.gabriel.ticketing.exception.BusinessException;
import com.gabriel.ticketing.exception.EventNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldCreateEventSuccessfully() {
        Event event = new Event("Rock in Rio", "Rio", LocalDateTime.now().plusDays(30), 100_000);

        ReflectionTestUtils.setField(event, "id", 1L);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventService.createEvent(event);

        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isEqualTo(1L);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void shouldThrowExceptionWhenEventNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.findById(1L))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event not found with id: 1");
    }

    @Test
    void shouldThrowExceptionWhenEventDateIsInThePast() {
        Event event = new Event("Evento Teste", "SP", LocalDateTime.now().minusDays(1), 100);

        assertThatThrownBy(() -> eventService.createEvent(event))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Event date cannot be in the past");
    }
}