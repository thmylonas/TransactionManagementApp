package com.thomasmylonas.transaction_microservice_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfig {

    @Bean(value = "webClient")
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
