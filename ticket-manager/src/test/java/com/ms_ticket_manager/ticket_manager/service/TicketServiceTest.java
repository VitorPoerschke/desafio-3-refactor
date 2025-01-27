package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.feign.EventClient;
import com.ms_ticket_manager.ticket_manager.model.Event;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @Mock
    private EventClient eventClient;

    @InjectMocks
    private TicketService service;

    @Test
    void testCreateTicketSuccess() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("Maria Silva");
        ticketDTO.setCpf("98765432100");
        ticketDTO.setCustomerMail("maria.silva@email.com");
        ticketDTO.setEventId("12345");
        ticketDTO.setBrlAmount("600.50");
        ticketDTO.setUsdAmount("19.80");

        Event event = new Event();
        event.setEventId("12345");
        event.setEventName("Evento Teste");

        Mockito.when(eventClient.getEventById("12345")).thenReturn(event);
        Mockito.when(repository.save(Mockito.any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ticket createdTicket = service.createTicket(ticketDTO);

        assertThat(createdTicket).isNotNull();
        assertThat(createdTicket.getCustomerName()).isEqualTo("Maria Silva");
        assertThat(createdTicket.getEvent().getEventName()).isEqualTo("Evento Teste");
        Mockito.verify(repository).save(Mockito.any(Ticket.class));
        Mockito.verify(eventClient).getEventById("12345");
    }

    @Test
    void testUpdateTicketSuccess() {

        Ticket existingTicket = new Ticket();
        existingTicket.setTicketId("123");
        existingTicket.setCustomerName("João");

        Ticket updatedTicket = new Ticket();
        updatedTicket.setCustomerName("Maria");

        Mockito.when(repository.findById("123")).thenReturn(Optional.of(existingTicket));
        Mockito.when(repository.save(Mockito.any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Ticket> result = service.updateTicket("123", updatedTicket);

        assertThat(result).isPresent();
        assertThat(result.get().getCustomerName()).isEqualTo("Maria");
        Mockito.verify(repository).findById("123");
        Mockito.verify(repository).save(Mockito.any(Ticket.class));
    }
    @Test
    void testDeleteTicketSuccess() {

        Ticket ticket = new Ticket();
        ticket.setTicketId("123");

        Mockito.when(repository.findById("123")).thenReturn(Optional.of(ticket));

        boolean wasDeleted = service.deleteTicket("123");

        assertThat(wasDeleted).isTrue();
        Mockito.verify(repository).deleteById("123");
    }

    @Test
    void testDeleteTicketNotFound() {
        Mockito.when(repository.findById("123")).thenReturn(Optional.empty());

        boolean wasDeleted = service.deleteTicket("123");

        assertThat(wasDeleted).isFalse();
        Mockito.verify(repository, Mockito.never()).deleteById(Mockito.anyString());
    }

}
