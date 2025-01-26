package com.ms_ticket_manager.ticket_manager.controller;

import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @Operation(summary = "Criar um novo ticket", description = "Cria um ticket com os detalhes fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Entrada inválida",
                    content = @Content)
    })
    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = service.createTicket(ticketDTO);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

   @GetMapping("get/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        return service.getTicketById(id)
               .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all-tickets")
      public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = service.getAllTickets();
        return ResponseEntity.ok(tickets);
     }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        boolean wasDeleted = service.deleteTicket(id);

        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable String id,
            @RequestBody Ticket updatedTicket) {
        return service.updateTicket(id, updatedTicket)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public String test() {
        return """
                ┌───────────────┐
                │ Servidor      │
                │ está ticket   │
                │ funcionando!  │
                └───────────────┘
                   ( ͡* ͜ʖ ͡*)
                """;
    }

}
