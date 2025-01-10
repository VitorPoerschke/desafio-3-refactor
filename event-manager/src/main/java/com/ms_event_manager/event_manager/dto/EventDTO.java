package com.ms_event_manager.event_manager.dto;

import lombok.Data;

@Data
public class EventDTO {
    private String eventName;
    private String dateTime;
    private String cep;
}