package com.ms_ticket_manager.ticket_manager.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private String eventId;
    private String eventName;
    private String eventDateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}