package com.gabriel.ticketing.controller.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gabriel.ticketing.auth.TokenService;
import com.gabriel.ticketing.domain.event.Event;
import com.gabriel.ticketing.domain.user.UserRepository;
import com.gabriel.ticketing.dto.event.CreateEventRequest;
import com.gabriel.ticketing.exception.BusinessException;
import com.gabriel.ticketing.exception.EventNotFoundException;
import com.gabriel.ticketing.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Test
    @WithMockUser
    void shouldCreateEventSuccessfully() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "Rock in Rio", "Rio de Janeiro", LocalDateTime.now().plusDays(30), 100_000
        );

        Event savedEvent = new Event(request.name(), request.location(), request.eventDate(), request.capacity());

        when(eventService.createEvent(any(Event.class))).thenReturn(savedEvent);

        mockMvc.perform(post("/api/events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(eventService).createEvent(any(Event.class));
    }

    @Test
    @WithMockUser
    void shouldFindEventById() throws Exception {
        Event event = new Event("Rock in Rio", "Rio de Janeiro", LocalDateTime.now().plusDays(30), 100_000);

        when(eventService.findById(1L)).thenReturn(event);

        mockMvc.perform(get("/api/events/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturn404WhenEventNotFound() throws Exception {
        when(eventService.findById(1L)).thenThrow(new EventNotFoundException(1L));

        mockMvc.perform(get("/api/events/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Event not found with id: 1"));
    }

    @Test
    @WithMockUser
    void shouldReturn400WhenCreateEventRequestIsInvalid() throws Exception {
        CreateEventRequest request = new CreateEventRequest("", "Rio", LocalDateTime.now().plusDays(1), 100);

        mockMvc.perform(post("/api/events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturn422WhenBusinessRuleIsViolated() throws Exception {
        CreateEventRequest request = new CreateEventRequest("Show", "SP", LocalDateTime.now().plusDays(10), 100);

        when(eventService.createEvent(any(Event.class)))
                .thenThrow(new BusinessException("Event date cannot be in the past"));

        mockMvc.perform(post("/api/events")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Event date cannot be in the past"));
    }
}