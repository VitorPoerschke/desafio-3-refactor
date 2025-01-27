package com.ms_ticket_manager.ticket_manager.feign;

import com.ms_ticket_manager.ticket_manager.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "http://localhost:8080/events/v1")
public interface EventClient {
    @GetMapping("/get-event/{id}")
    Event getEventById(@PathVariable("id") String id);
}