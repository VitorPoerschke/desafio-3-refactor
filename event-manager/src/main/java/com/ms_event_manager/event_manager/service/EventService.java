package com.ms_event_manager.event_manager.service;

import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.feign.ViaCepClient;
import lombok.RequiredArgsConstructor;
import com.ms_event_manager.event_manager.model.Event;
import org.springframework.stereotype.Service;
import com.ms_event_manager.event_manager.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final ViaCepClient viaCepClient;

    public Event createEvent(EventDTO dto) {

        Event event = new Event();
        event.setEventName(dto.getEventName());
        event.setDateTime(dto.getDateTime());
        event.setCep(dto.getCep());

        var cepDetails = viaCepClient.getCepDetails(dto.getCep());
        event.setLogradouro(cepDetails.get("logradouro"));
        event.setBairro(cepDetails.get("bairro"));
        event.setCidade(cepDetails.get("localidade"));
        event.setUf(cepDetails.get("uf"));
        return repository.save(event);
    }

    public Optional<Event> getEventById(String id) {
        return repository.findById(id);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public void deleteEvent(String id) {
        repository.deleteById(id);
    }
}