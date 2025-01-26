package com.ms_ticket_manager.ticket_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    private TicketDTO ticketDTO;
    private Ticket ticket;

    @BeforeEach
    void setUp() {

        ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("John Doe");
        ticketDTO.setCpf("12345678901");
        ticketDTO.setCustomerMail("john.doe@example.com");
        ticketDTO.setEventId("1");
        ticketDTO.setBrlAmount("100.00");
        ticketDTO.setUsdAmount("20.00");

        ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setCustomerName("John Doe");
        ticket.setCpf("12345678901");
        ticket.setCustomerMail("john.doe@example.com");
        ticket.setEventId("1");
        ticket.setBrlAmount("100.00");
        ticket.setUsdAmount("20.00");
        ticket.setStatus("concluído");
    }

    @Test
    void createTicket_shouldReturnCreatedTicket() throws Exception {

        Mockito.when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticket);

        mockMvc.perform(post("/v1/tickets/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId").value("1"))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void getTicketById_shouldReturnTicketWhenFound() throws Exception {

        Mockito.when(ticketService.getTicketById(eq("1"))).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/v1/tickets/get/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value("1"))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void getTicketById_shouldReturnNotFoundWhenNotFound() throws Exception {

        Mockito.when(ticketService.getTicketById(eq("1"))).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/tickets/get/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTickets_shouldReturnListOfTickets() throws Exception {

        Mockito.when(ticketService.getAllTickets()).thenReturn(Arrays.asList(ticket));

        mockMvc.perform(get("/v1/tickets/get-all-tickets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticketId").value("1"))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    void deleteTicket_shouldReturnNoContentWhenDeleted() throws Exception {

        Mockito.when(ticketService.deleteTicket(eq("1"))).thenReturn(true);

        mockMvc.perform(delete("/v1/tickets/delete/1"))
                .andExpect(status().isNoContent());
    }


}//!
