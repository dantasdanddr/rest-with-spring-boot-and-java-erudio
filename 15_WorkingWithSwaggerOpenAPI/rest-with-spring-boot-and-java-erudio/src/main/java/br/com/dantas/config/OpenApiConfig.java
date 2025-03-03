package br.com.dantas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("REST API's RESTful from 0 with Java, Spring Boot, K8s and Docker")
                .version("v1")
                .description("REST API's RESTful from 0 with Java, Spring Boot, K8s and Docker")
                .termsOfService("https://github.com/dantasdanddr")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://github.com/dantasdanddr"))
            );
    }
}
