package com.example.hotelbao.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define o nome do esquema de segurança. Usaremos este nome abaixo.
        final String securitySchemeName = "bearerAuth";

        // Cria e retorna a configuração completa da documentação OpenAPI.
        return new OpenAPI()
                // Adiciona um item de requisito de segurança global.
                // Isto faz com que o ícone de cadeado apareça em todos os endpoints que exigem
                // autenticação.
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                // Define os componentes da API, incluindo os esquemas de segurança.
                .components(
                        new Components()
                                // Adiciona o esquema de segurança "Bearer Authentication".
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP) // O tipo é HTTP.
                                                .scheme("bearer") // O esquema é "bearer".
                                                .bearerFormat("JWT") // O formato é JWT.
                                ))

                // Adiciona informações gerais sobre a API (título, versão, etc.).
                .info(new Info()
                        .title("API do Hotel BAO")
                        .version("v1")
                        .description("API para o sistema de gestão de estadias do Hotel BAO.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}