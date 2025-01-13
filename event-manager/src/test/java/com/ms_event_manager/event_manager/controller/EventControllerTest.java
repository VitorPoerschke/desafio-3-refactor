package com.ms_event_manager.event_manager.controller;

import com.ms_event_manager.event_manager.dto.EventDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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



}
