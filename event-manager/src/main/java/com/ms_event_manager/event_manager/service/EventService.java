package com.ms_event_manager.event_manager.service;

import com.ms_event_manager.event_manager.repository.EventRepository;
import com.ms_event_manager.event_manager.feign.ViaCepClient;
import com.ms_event_manager.event_manager.dto.EventDTO;
import com.ms_event_manager.event_manager.model.Event;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final ViaCepClient viaCepClient;

    public Event createEvent(EventDTO dto) {

        if (dto.getEventName() == null || dto.getEventName().isBlank()) {
            throw new IllegalArgumentException("O nome do evento não pode ser vazio ou nulo!");
        }
        if (dto.getCep() == null || dto.getCep().isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }

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

    public List<Event> getAllEventsSorted() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "eventName"));
    }

    public Optional<Event> updateEvent(String id, Event dto) {

        if (dto.getEventName() == null || dto.getEventName().isBlank()) {
            throw new IllegalArgumentException("O nome do evento não pode ser vazio ou  nulo!");
        }

        if (dto.getCep() == null || dto.getCep().isBlank()) {
            throw new IllegalArgumentException("O CEP é atualizado não pode ser vazio ou nulo!");
        }

        return repository.findById(id).map(existingEvent -> {

            existingEvent.setEventName(dto.getEventName());
            existingEvent.setDateTime(dto.getDateTime());
            existingEvent.setCep(dto.getCep());

            var cepDetails = viaCepClient.getCepDetails(dto.getCep());
            existingEvent.setLogradouro(cepDetails.get("logradouro"));
            existingEvent.setBairro(cepDetails.get("bairro"));
            existingEvent.setCidade(cepDetails.get("localidade"));
            existingEvent.setUf(cepDetails.get("uf"));

            return repository.save(existingEvent);
        });
    }


    public void deleteEvent(String id) {
        repository.deleteById(id);
    }

}