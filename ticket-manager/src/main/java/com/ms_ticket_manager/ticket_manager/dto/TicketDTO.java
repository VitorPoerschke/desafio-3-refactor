package com.ms_ticket_manager.ticket_manager.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String assignedTo;
}