package com.ms_ticket_manager.ticket_manager.rabbit;

import com.ms_ticket_manager.ticket_manager.model.EmailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class EmailConsumer {

    private final JavaMailSender mailSender;

    public EmailConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consumeEmailMessage(EmailMessage emailMessage) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getTo());
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getBody());

            mailSender.send(message);

            System.out.println("E-mail enviado com sucesso para: " + emailMessage.getTo());
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
