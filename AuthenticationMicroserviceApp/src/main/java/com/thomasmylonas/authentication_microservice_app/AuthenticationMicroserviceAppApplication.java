package com.thomasmylonas.authentication_microservice_app;

import com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:app_properties/constants_properties.properties")
public class AuthenticationMicroserviceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationMicroserviceAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TransactionRepository transactionRepository) {
        return args -> {
            transactionRepository.saveAll(HardCodedTestDataProvider.TRANSACTIONS_LIST_HARD_CODED);
        };
    }
}
