package com.ms_ticket_manager.ticket_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class
TicketManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketManagerApplication.class, args);
	}

}
