package com.ms_event_manager.event_manager.controller;

import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.exceptions.NotFoundException;
import com.ms_event_manager.event_manager.model.Event;
import com.ms_event_manager.event_manager.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @InjectMocks
    private EventController controller;

    @Mock
    private EventService service;

    @Test
    void testCreateEvent_Success() {
        EventDTO dto = new EventDTO();
        dto.setEventName("Evento Teste");
        dto.setDateTime("2025-01-12T15:00:00");
        dto.setCep("12345678");
        Event event = new Event();
        event.setId("1");
        event.setEventName("Evento Teste");
        event.setDateTime("2025-01-12T15:00:00");
        event.setCep("12345678");
        event.setLogradouro("Rua X");
        event.setBairro("Centro");
        event.setCidade("São Paulo");
        event.setUf("SP");
        when(service.createEvent(dto)).thenReturn(event);
        ResponseEntity<Event> response = controller.createEvent(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(event, response.getBody());
    }

    @Test
    void testCreateEvent_InvalidCep() {
        EventDTO dto = new EventDTO();
        dto.setEventName("Evento Teste");
        dto.setDateTime("2025-01-12T15:00:00");
        dto.setCep("123");
        Mockito.when(service.createEvent(dto))
                .thenThrow(new IllegalArgumentException("CEP inválido"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEvent(dto);
        });
        assertEquals("CEP inválido", exception.getMessage());
    }

    @Test
    void testCreateEvent_MissingData() {
        EventDTO dto = new EventDTO();
        dto.setEventName("");
        dto.setDateTime("2025-01-12T15:00:00");
        dto.setCep("12345678");
        Mockito.when(service.createEvent(dto))
                .thenThrow(new IllegalArgumentException("Nome do evento não pode ser vazio"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEvent(dto);
        });
        assertEquals("Nome do evento não pode ser vazio", exception.getMessage());
    }

    @Test
    void testCreateEvent_GenericException() {
        EventDTO dto = new EventDTO();
        dto.setEventName("Evento Teste");
        dto.setDateTime("2025-01-12T15:00:00");
        dto.setCep("82734561");
        Mockito.when(service.createEvent(dto))
                .thenThrow(new RuntimeException("Erro inesperado"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.createEvent(dto);
        });
        assertEquals("Erro inesperado", exception.getMessage());
    }

    //apartir daqui
    @Test
    void testGetEventById_Success() {
        String id = "1";
        Event event = new Event();
        event.setId("1");
        event.setEventName("Evento Teste");
        event.setDateTime("2025-01-12T15:00:00");
        event.setCep("12345678");
        event.setLogradouro("Rua X");
        event.setBairro("Centro");
        event.setCidade("São Paulo");
        event.setUf("SP");
        when(service.getEventById(id)).thenReturn(Optional.of(event));
        ResponseEntity<Event> response = controller.getEventById(id);
        assertEquals(event, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetEventById_NotFound() {
        String id = "1";
        when(service.getEventById(id)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.getEventById(id);
        });
        assertEquals("Evento com ID 1 não encontrado.", exception.getMessage());
    }

    @Test
    void testGetAllEvents_Success() {
        Event event1 = new Event();
        event1.setId("1");
        event1.setEventName("Evento 1");
        event1.setDateTime("2025-01-12T15:00:00");
        event1.setCep("12345678");
        event1.setLogradouro("Rua A");
        event1.setBairro("Bairro A");
        event1.setCidade("Cidade A");
        event1.setUf("UF");
        Event event2 = new Event();
        event2.setId("2");
        event2.setEventName("Evento 2");
        event2.setDateTime("2025-01-13T15:00:00");
        event2.setCep("87654321");
        event2.setLogradouro("Rua B");
        event2.setBairro("Bairro B");
        event2.setCidade("Cidade B");
        event2.setUf("UF");
        List<Event> events = Arrays.asList(event1, event2);
        when(service.getAllEvents()).thenReturn(events);
        ResponseEntity<List<Event>> response = controller.getAllEvents();
        assertEquals(events, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllEvents_EmptyList() {
        when(service.getAllEvents()).thenReturn(Collections.emptyList());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getAllEvents();
        });
        assertEquals("Nenhum evento encontrado.", exception.getMessage());
    }

    @Test
    void testGetAllEventsSorted_Success() {
        Event event1 = new Event();
        event1.setId("1");
        event1.setEventName("Evento A");
        event1.setDateTime("2025-01-12T15:00:00");
        event1.setCep("12345678");
        event1.setLogradouro("Rua X");
        event1.setBairro("Bairro X");
        event1.setCidade("Cidade X");
        event1.setUf("UF");
        Event event2 = new Event();
        event2.setId("2");
        event2.setEventName("Evento B");
        event2.setDateTime("2025-01-13T15:00:00");
        event2.setCep("87654321");
        event2.setLogradouro("Rua Y");
        event2.setBairro("Bairro Y");
        event2.setCidade("Cidade Y");
        event2.setUf("UF");
        List<Event> sortedEvents = Arrays.asList(event1, event2);
        when(service.getAllEventsSorted()).thenReturn(sortedEvents);
        ResponseEntity<List<Event>> response = controller.getAllEventsSorted();
        assertEquals(sortedEvents, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllEventsSorted_EmptyList() {
        when(service.getAllEventsSorted()).thenReturn(Collections.emptyList());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getAllEventsSorted();
        });
        assertEquals("Nenhum evento encontrado para ordenação.", exception.getMessage());
    }

    @Test
    void testUpdateEvent_Success() {
        String id = "1";
        Event updatedEvent = new Event();
        updatedEvent.setId("1");
        updatedEvent.setEventName("Evento Atualizado");
        updatedEvent.setDateTime("2025-01-12T15:00:00");
        updatedEvent.setCep("12345678");
        updatedEvent.setLogradouro("Rua Atualizada");
        updatedEvent.setBairro("Bairro Atualizado");
        updatedEvent.setCidade("Cidade Atualizada");
        updatedEvent.setUf("UF");
        when(service.updateEvent(eq(id), any(Event.class))).thenReturn(Optional.of(updatedEvent));
        ResponseEntity<Event> response = controller.updateEvent(id, updatedEvent);
        assertEquals(updatedEvent, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }



}
