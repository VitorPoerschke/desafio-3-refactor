package com.ms_event_manager.event_manager.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private String dateTime;
    private String cep;
}