package com.thomasmylonas.authentication_microservice_app;

//import com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider;

import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
//import com.thomasmylonas.authentication_microservice_app.services.TransactionService;
import com.thomasmylonas.authentication_microservice_app.services.TransactionServiceImpl;
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
    public CommandLineRunner commandLineRunner(TransactionRepository transactionRepository, TransactionServiceImpl transactionService) {
        return args -> {
//            transactionRepository.saveAll(HardCodedTestDataProvider.TRANSACTIONS_LIST_HARD_CODED);
//            transactionRepository.saveAll(transactionService.generateTransactions(5));
            transactionService.sendTransactions("http://localhost:8080/transactions/save-all",
                    transactionService.generateTransactions(5));
        };
    }
}

/*
SELECT * FROM TRANSACTIONS;
SELECT * FROM TRANSACTIONS_DATA;
*/