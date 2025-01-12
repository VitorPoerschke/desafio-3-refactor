package com.ms_event_manager.event_manager.controller;

import com.ms_event_manager.event_manager.dto.EventDTO;
import lombok.RequiredArgsConstructor;
import com.ms_event_manager.event_manager.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ms_event_manager.event_manager.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events/v1")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO dto) {
        Event event = service.createEvent(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return  service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

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
}