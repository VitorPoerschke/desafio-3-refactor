package com.ms_event_manager.event_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Criar evento", description = "Cria um novo evento com base nos dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso!", content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida -_-¨!", content = @Content)
    })
    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO dto) {
        Event event = service.createEvent(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar evento por ID", description = "Obtém os detalhes de um evento usando seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado", content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Evento erro não encontrado", content = @Content)
    })
    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Evento com ID " + id + " não encontrado."));
    }

    @Operation(summary = "Listar todos os eventos", description = "Retorna todos os eventos disponíveis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso", content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado", content = @Content)
    })
    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = service.getAllEvents();
        if (events.isEmpty()) {
            throw new NotFoundException("Nenhum evento encontrado.");
        }
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Listar eventos ordenados", description = "Retorna todos os eventos em ordem especificada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos retornados e ordenados com sucesso", content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado para ordenação", content = @Content)
    })
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> sortedEvents = service.getAllEventsSorted();
        if (sortedEvents.isEmpty()) {
            throw new NotFoundException("Nenhum evento encontrado para ordenação.");
        }
        return ResponseEntity.ok(sortedEvents);
    }

    @Operation(summary = "Atualizar evento", description = "Atualiza os detalhes de um evento existente usando seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso", content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event event) {
        return service.updateEvent(id, event)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Evento com ID " + id + " não encontrado para atualizar."));
    }

    @Operation(summary = "Excluir evento", description = "Exclui um evento com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evento excluído com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        try {
            service.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Evento com ID " + id + " não encontrado para exclusão.");
        }
    }

    @Operation(summary = "Testar servidor", description = "Testa se o servidor está ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servidor funcionando", content = @Content)
    })
    @GetMapping("/test")
    public String test() {
        return """
                ┌───────────────┐
                │ Servidor      │
                │ está evento   │
                │ funcionando!  │
                └───────────────┘
                   ( ͡* ͜ʖ ͡*)
                """;
    }

}