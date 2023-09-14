package com.thomasmylonas.authentication_microservice_app;

import com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider;
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
    public CommandLineRunner commandLineRunner(TransactionServiceImpl transactionService) {

        return args -> {

            /*
            // Create Hardcoded test data
            // First inject the "com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository"
            // through the "AuthenticationMicroserviceAppApplication::commandLineRunner" parameters
            transactionRepository.saveAll(com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider.TRANSACTIONS_LIST_HARD_CODED);
            */

            createTestTransactionsInThisMicroservice(transactionService);
            sendTransactionsInTransactionMicroservice(transactionService);
        };
    }

    private static void createTestTransactionsInThisMicroservice(TransactionServiceImpl transactionService) {
        transactionService.sendTransactions("http://localhost:8080/transactions/save-all",
                transactionService.generateTransactions(HardCodedTestDataProvider.AMOUNT_OF_AUTO_GENERATED_TRANSACTIONS));
    }

    private static void sendTransactionsInTransactionMicroservice(TransactionServiceImpl transactionService) {
        transactionService.sendTransactions("http://localhost:8081/transactions/save-all",
                transactionService.generateTransactions(HardCodedTestDataProvider.AMOUNT_OF_AUTO_GENERATED_TRANSACTIONS));
    }
}
