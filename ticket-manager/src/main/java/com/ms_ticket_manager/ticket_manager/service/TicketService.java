package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.feign.EventClient;
import com.ms_ticket_manager.ticket_manager.model.Event;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final EventClient eventClient;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setBRLtotalAmount(ticketDTO.getBrlAmount());
        ticket.setUSDtotalAmount(ticketDTO.getUsdAmount());
        ticket.setEventId(ticketDTO.getEventId());

        log.info("Buscando evento com ID: {}", ticketDTO.getEventId());
        Event event = eventClient.getEventById(ticketDTO.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        ticket.setEvent(event);
        ticket.setStatus("concluído");

        return repository.save(ticket);
    }

    public Optional<Ticket> getTicketById(String id) {
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public boolean deleteTicket(String id) {
        Optional<Ticket> ticket = repository.findById(id);
        if (ticket.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Ticket> updateTicket(String id, Ticket updatedTicket) {
        return repository.findById(id).map(existingTicket -> {
            if (updatedTicket.getCpf() != null) {
                existingTicket.setCpf(updatedTicket.getCpf());
            }
            if (updatedTicket.getCustomerName() != null) {
                existingTicket.setCustomerName(updatedTicket.getCustomerName());
            }
            if (updatedTicket.getCustomerMail() != null) {
                existingTicket.setCustomerMail(updatedTicket.getCustomerMail());
            }
            if (updatedTicket.getEventId() != null) {
                existingTicket.setEventId(updatedTicket.getEventId());
            }
            if (updatedTicket.getBRLtotalAmount() != null) {
                existingTicket.setBRLtotalAmount(updatedTicket.getBRLtotalAmount());
            }
            if (updatedTicket.getUSDtotalAmount() != null) {
                existingTicket.setUSDtotalAmount(updatedTicket.getUSDtotalAmount());
            }
            if (updatedTicket.getStatus() != null) {
                existingTicket.setStatus(updatedTicket.getStatus());
            }
            return repository.save(existingTicket);
        });
    }

}