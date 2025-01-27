package com.ms_ticket_manager.ticket_manager.Swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tickets Manager API")
                        .version("1.0.0")
                        .description("API para gerenciamento de tickets.")
                );
    }
}