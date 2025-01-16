package com.ms_ticket_manager.ticket_manager.repository;

import com.ms_ticket_manager.ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}