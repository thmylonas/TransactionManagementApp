package com.thomasmylonas.authentication_microservice_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfig {

//    @Value(value = "${jsonplaceholder.url}")
//    private String jsonplaceholderUrl;

    @Bean(value = "webClient")
    public WebClient webClient() {
        return WebClient.create("jsonplaceholderUrl");
    }
}
