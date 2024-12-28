package service;

import dto.EventDTO;
import feign.ViaCepClient;
import lombok.RequiredArgsConstructor;
import model.Event;
import org.springframework.stereotype.Service;
import repository.EventRepository;

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

    public Iterable<Event> getAllEvents() {
        return repository.findAll();
    }

    public void deleteEvent(String id) {
        repository.deleteById(id);
    }
}