package com.ms_event_manager.event_manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ms_event_manager.event_manager.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
}