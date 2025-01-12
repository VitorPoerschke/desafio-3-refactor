package com.ms_event_manager.event_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.model.Event;
import com.ms_event_manager.event_manager.service.EventService;
import java.util.List;
import lombok.RequiredArgsConstructor;

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

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        return ResponseEntity.ok(service.getAllEventsSorted());
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