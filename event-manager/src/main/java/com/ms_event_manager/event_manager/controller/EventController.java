package com.ms_event_manager.event_manager.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.model.Event;
import com.ms_event_manager.event_manager.service.EventService;
import com.ms_event_manager.event_manager.exceptions.NotFoundException;
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
        return service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Evento com ID " + id + " não encontrado."));
    }//ok 1 <-

    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = service.getAllEvents();
        if (events.isEmpty()) {
            throw new NotFoundException("Nenhum evento encontrado.");
        }
        return ResponseEntity.ok(events);
    }//ok 3 <-

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> sortedEvents = service.getAllEventsSorted();
        if (sortedEvents.isEmpty()) {
            throw new NotFoundException("Nenhum evento encontrado para ordenação.");
        }
        return ResponseEntity.ok(sortedEvents);
    }//ok 4 <-

    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event event) {
        return service.updateEvent(id, event)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Evento com ID " + id + " não encontrado para atualizar."));
    }//ok 2 <-

    @GetMapping("/test")
    public String test() {
        return """
                ┌───────────────┐
                │ Servidor      │
                │ está          │
                │ funcionando!  │
                └───────────────┘
                   ( ͡* ͜ʖ ͡*)
                """;
    }

}