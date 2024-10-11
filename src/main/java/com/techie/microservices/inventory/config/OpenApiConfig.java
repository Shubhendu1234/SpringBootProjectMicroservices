package com.techie.microservices.inventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventoryServiceopenAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Service API")
                        .description("This is REST API for Inventory Service")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer inventory service wiki Documentation")
                        .url("https://inventory-service-dummyurl.com/docs"));
    }
}
