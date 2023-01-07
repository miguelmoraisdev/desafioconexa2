package com.desafioconexa.miguelm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(this.metaData());
    }

    private Info metaData() {
        return new Info()
                .title("Conexa Test 2")
                .description("Codes for the Conexa Test 2")
                .version("1.0.0");
    }

}