package com.loja.bolos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
            .title("Generated API")
            .description("Sistema de Gestão de Loja de Bolos com autenticação via Keycloak.")
            .version("v1"));
    }
}
