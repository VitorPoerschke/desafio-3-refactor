package dto;

import lombok.Data;

@Data
public class EventDTO {
    private String eventName;
    private String dateTime;
    private String cep;
}