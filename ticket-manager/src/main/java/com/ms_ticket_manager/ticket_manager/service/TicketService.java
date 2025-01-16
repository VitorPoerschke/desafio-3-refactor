package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

}
