package com.ms_ticket_manager.ticket_manager.rabbit;

import com.ms_ticket_manager.ticket_manager.model.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailMessage(EmailMessage emailMessage) {
        rabbitTemplate.convertAndSend(exchange, routingKey, emailMessage);
        System.out.println("Mensagem enviada para a fila: " + emailMessage);
    }
}
