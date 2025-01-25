package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.feign.EventClient;
import com.ms_ticket_manager.ticket_manager.model.Event;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}