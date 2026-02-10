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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldCreateEventSuccessfully() {
        Event event = new Event(
                "Rock in Rio",
                "Rio de Janeiro",
                LocalDateTime.now().plusDays(30),
                100_000
        );

        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventService.createEvent(event);

        assertThat(savedEvent).isNotNull();
        verify(eventRepository).save(event);
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
        Event event = new Event(
                "Evento Teste",
                "São Paulo",
                LocalDateTime.now().minusDays(1),
                100
        );

        assertThatThrownBy(() -> eventService.createEvent(event))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Event date cannot be in the past");
    }

}
