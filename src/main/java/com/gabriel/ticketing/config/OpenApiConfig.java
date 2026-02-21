package com.gabriel.ticketing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketingSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticketing System API")
                        .description("API for ticket management with events, users and messaging")
                        .version("v1"));
    }
}
