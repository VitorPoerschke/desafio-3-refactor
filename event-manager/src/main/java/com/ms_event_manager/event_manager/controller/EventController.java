package com.ms_event_manager.event_manager.controller;

import com.ms_event_manager.event_manager.dto.EventDTO;
import lombok.RequiredArgsConstructor;
import com.ms_event_manager.event_manager.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ms_event_manager.event_manager.service.EventService;

@RestController
@RequestMapping("/events/v1")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping("/test")
    public String test() {
        return """
                ┌───────────────┐
                │ Servidor      │
                │ está          │
                │ funcionando!  │
                └───────────────┘
                   ( ͡° ͜ʖ ͡°)
                """;
    }

    @PostMapping("/create_event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO dto) {
        Event event = service.createEvent(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }



}