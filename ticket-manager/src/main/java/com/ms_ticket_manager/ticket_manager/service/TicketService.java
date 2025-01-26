package com.ms_ticket_manager.ticket_manager.service;

import com.ms_ticket_manager.ticket_manager.dto.TicketDTO;
import com.ms_ticket_manager.ticket_manager.feign.EventClient;
import com.ms_ticket_manager.ticket_manager.model.EmailMessage;
import com.ms_ticket_manager.ticket_manager.model.Event;
import com.ms_ticket_manager.ticket_manager.model.Ticket;
import com.ms_ticket_manager.ticket_manager.rabbit.RabbitMQConfig;
import com.ms_ticket_manager.ticket_manager.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final EventClient eventClient;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public Ticket createTicket(TicketDTO ticketDTO) {

        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());
        ticket.setEventId(ticketDTO.getEventId());

        log.info("Buscando evento com ID: {}", ticketDTO.getEventId());
        Event event = eventClient.getEventById(ticketDTO.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        ticket.setEvent(event);
        ticket.setStatus("concluído");

        Ticket savedTicket = repository.save(ticket);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(ticket.getCustomerMail());
        emailMessage.setSubject("🎉 Parabéns pelo seu ingresso! 🎟️");

        String emailBody = String.format(
                "Olá %s!\n\n" +
                        "Você acaba de comprar um ingresso para o evento *%s*! 🎊\n" +
                        "Detalhes do evento:\n" +
                        "- Nome: %s\n" +
                        "- Local: %s\n" +
                        "- Data: %s\n\n" +
                        "Valor total pago: R$ %s\n\n" +
                        "Muito obrigado por sua compra! Esperamos que você aproveite o evento ao máximo. 😄\n" +
                        "Caso tenha dúvidas, entre em contato conosco.\n\n" +
                        "Equipe do Evento Manager 💌",
                ticket.getCustomerName(),
                event.getEventName(),
                event.getUf(),
                event.getCidade(),
                event.getEventDateTime(),
                ticket.getBrlAmount()
        );

        emailMessage.setBody(emailBody);

        rabbitTemplate.convertAndSend("email-exchange", "email-routing-key", emailMessage);
        log.info("Mensagem de e-mail enviada para fila RabbitMQ para o cliente: {}", ticket.getCustomerMail());

        return savedTicket;
    }

    public Optional<Ticket> getTicketById(String id) {
        return repository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public boolean deleteTicket(String id) {
        Optional<Ticket> ticket = repository.findById(id);
        if (ticket.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Ticket> updateTicket(String id, Ticket updatedTicket) {
        return repository.findById(id).map(existingTicket -> {
            if (updatedTicket.getCpf() != null) {
                existingTicket.setCpf(updatedTicket.getCpf());
            }
            if (updatedTicket.getCustomerName() != null) {
                existingTicket.setCustomerName(updatedTicket.getCustomerName());
            }
            if (updatedTicket.getCustomerMail() != null) {
                existingTicket.setCustomerMail(updatedTicket.getCustomerMail());
            }
            if (updatedTicket.getEventId() != null) {
                existingTicket.setEventId(updatedTicket.getEventId());
            }
            if (updatedTicket.getBrlAmount() != null) {
                existingTicket.setBrlAmount(updatedTicket.getBrlAmount());
            }
            if (updatedTicket.getUsdAmount() != null) {
                existingTicket.setUsdAmount(updatedTicket.getUsdAmount());
            }
            if (updatedTicket.getStatus() != null) {
                existingTicket.setStatus(updatedTicket.getStatus());
            }
            return repository.save(existingTicket);
        });
    }

}