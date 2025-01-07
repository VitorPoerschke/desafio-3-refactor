package controller;

import dto.EventDTO;
import lombok.RequiredArgsConstructor;
import model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EventService;

@RestController
@RequestMapping("/events/v1")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO dto) {
        Event event = service.createEvent(dto);
        return ResponseEntity.ok(event);
    }



}