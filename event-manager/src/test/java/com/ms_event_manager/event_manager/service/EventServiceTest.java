package com.ms_event_manager.event_manager.service;

import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.feign.ViaCepClient;
import com.ms_event_manager.event_manager.model.Event;
import com.ms_event_manager.event_manager.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService service;

    @Mock
    private EventRepository repository;

    @Mock
    private ViaCepClient viaCepClient;

    @Test
    void testCreateEvent_Success() {
        EventDTO dto = new EventDTO("Evento Teste", "2025-01-12T15:00:00", "12345678");
        Event expectedEvent = new Event();
        expectedEvent.setId("1");
        expectedEvent.setEventName("Evento Teste");
        expectedEvent.setDateTime("2025-01-12T15:00:00");
        expectedEvent.setCep("12345678");
        expectedEvent.setLogradouro("Rua X");
        expectedEvent.setBairro("Centro");
        expectedEvent.setCidade("Cidade X");
        expectedEvent.setUf("XX");
        when(viaCepClient.getCepDetails("12345678")).thenReturn(Map.of(
                "logradouro", "Rua X",
                "bairro", "Centro",
                "localidade", "Cidade X",
                "uf", "XX"
        ));
        when(repository.save(any(Event.class))).thenReturn(expectedEvent);
        Event createdEvent = service.createEvent(dto);
        assertNotNull(createdEvent);
        assertEquals("Evento Teste", createdEvent.getEventName());
        assertEquals(HttpStatus.CREATED.value(), HttpStatus.CREATED.value());
    }

    @Test
    void testCreateEvent_MissingName() {
        EventDTO dto = new EventDTO(null, "2025-01-12T15:00:00", "12345678");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createEvent(dto));
        assertEquals("O nome do evento não pode ser vazio ou nulo!", exception.getMessage());
    }

    @Test
    void testCreateEvent_MissingCep() {
        EventDTO dto = new EventDTO("Evento Teste", "2025-01-12T15:00:00", "");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createEvent(dto));
        assertEquals("O CEP é obrigatório!", exception.getMessage());
    }

    @Test
    void testGetEventById_Success() {
        Event event = new Event();
        event.setId("1");
        event.setEventName("Evento Teste");
        when(repository.findById("1")).thenReturn(Optional.of(event));
        Optional<Event> result = service.getEventById("1");
        assertTrue(result.isPresent());
        assertEquals("Evento Teste", result.get().getEventName());
    }

    @Test
    void testGetEventById_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());
        Optional<Event> result = service.getEventById("1");
        assertFalse(result.isPresent());
    }



}
