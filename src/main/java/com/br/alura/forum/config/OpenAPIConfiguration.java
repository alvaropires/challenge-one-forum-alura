package com.br.alura.forum.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info().title("Forum Alura API")
                        .description("API do Forum Alura")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://forum.alura.com.br")))
                .externalDocs(new ExternalDocumentation()
                        .description("Forum Alura API DOCS")
                        .url("https://forum.alura.com.br"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").
                                in(SecurityScheme.In.HEADER)
                                .bearerFormat("JWT")));
    }
}
