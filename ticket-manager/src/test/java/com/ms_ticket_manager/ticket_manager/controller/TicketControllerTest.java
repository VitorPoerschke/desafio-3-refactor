package com.ms_ticket_manager.ticket_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_ticket_manager.ticket_manager.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import lombok.RequiredArgsConstructor;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testCreateTicket() throws Exception {

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("João Silva");
        ticketDTO.setCpf("12345678901");
        ticketDTO.setCustomerMail("joao.silva@example.com");
        ticketDTO.setEventId("event123");
        ticketDTO.setBrlAmount("100.00");
        ticketDTO.setUsdAmount("20.00");

        Ticket ticket = new Ticket();
        ticket.setTicketId("ticket123");
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setBRLtotalAmount(ticketDTO.getBrlAmount());
        ticket.setUSDtotalAmount(ticketDTO.getUsdAmount());
        ticket.setStatus("concluído");

        Mockito.when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticket);

        mockMvc.perform(post("/v1/tickets/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId").value("ticket123"))
                .andExpect(jsonPath("$.customerName").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.customerMail").value("joao.silva@example.com"))
                .andExpect(jsonPath("$.eventId").value("event123"))
                .andExpect(jsonPath("$.BRLtotalAmount").value("100.00"))
                .andExpect(jsonPath("$.USDtotalAmount").value("20.00"))
                .andExpect(jsonPath("$.status").value("concluído"));
    }

}
