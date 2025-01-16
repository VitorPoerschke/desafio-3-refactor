package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    public Ticket createTicket(Ticket ticket) {
        return repository.save(ticket);
    }

    public Optional<Ticket> getTicketById(String id) {
        return repository.findById(id)  ;
    }
    
    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

}
