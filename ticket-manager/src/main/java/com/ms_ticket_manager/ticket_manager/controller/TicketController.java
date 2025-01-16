package com.ms_ticket_manager.ticket_manager.controller;

import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;


    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = service.createTicket(ticket);
        return ResponseEntity.ok(createdTicket);
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
        service.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
