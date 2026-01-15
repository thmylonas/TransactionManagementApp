package com.thomasmylonas.authentication_microservice_app;

import com.thomasmylonas.authentication_microservice_app.helpers.TestDataProvider;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import com.thomasmylonas.authentication_microservice_app.services.TransactionServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationMicroserviceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationMicroserviceAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TransactionServiceImpl transactionService) {

        return args -> {

            /*
            // Create Hardcoded test data
            // First inject the "TransactionRepository" through the "AuthenticationMicroserviceAppApplication::commandLineRunner" parameters
            createHardCodedTestDataInThisMicroservice(transactionRepository);
            */

            createTestTransactionsInThisMicroservice(transactionService);
            sendTransactionsInTransactionMicroservice(transactionService);
        };
    }

    private void createHardCodedTestDataInThisMicroservice(TransactionRepository transactionRepository) {
        transactionRepository.saveAll(TestDataProvider.TRANSACTIONS_LIST);
    }

    private void createTestTransactionsInThisMicroservice(TransactionServiceImpl transactionService) {
        transactionService.sendTransactions("http://localhost:8080/api/v1/transactions/all",
                transactionService.generateTransactions(TestDataProvider.AMOUNT_OF_AUTO_GENERATED_TRANSACTIONS));
    }

    private void sendTransactionsInTransactionMicroservice(TransactionServiceImpl transactionService) {
        transactionService.sendTransactions("http://localhost:8081/api/v1/transactions/all",
                transactionService.generateTransactions(TestDataProvider.AMOUNT_OF_AUTO_GENERATED_TRANSACTIONS));
    }
}
