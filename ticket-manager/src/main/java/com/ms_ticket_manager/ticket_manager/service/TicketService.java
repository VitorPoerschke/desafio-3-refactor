package com.ms_ticket_manager.ticket_manager.service;

import java.util.UUID;
import java.util.Optional;
import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.feign.EventClient;
import com.ms_ticket_manager.ticket_manager.model.Event;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        Event event = eventClient.getEventById(ticketDTO.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        ticket.setEvent(event);
        ticket.setStatus("concluído");

        return ticketRepository.save(ticket);
    }
}