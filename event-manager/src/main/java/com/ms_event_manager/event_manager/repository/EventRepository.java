package com.ms_event_manager.event_manager.repository;

import com.ms_event_manager.event_manager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}