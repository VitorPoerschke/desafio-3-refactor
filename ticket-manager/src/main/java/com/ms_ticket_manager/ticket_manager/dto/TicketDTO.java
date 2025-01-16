package com.ms_ticket_manager.ticket_manager.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
}