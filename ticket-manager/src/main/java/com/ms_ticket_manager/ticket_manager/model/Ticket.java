package com.ms_ticket_manager.ticket_manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String assignedTo;
    private String createdAt;
    private String updatedAt;
}