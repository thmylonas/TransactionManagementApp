package com.thomasmylonas.authentication_microservice_app;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@PropertySource(value = "classpath:app_properties/constants_properties.properties")
public class AuthenticationMicroserviceAppApplication {

    private final List<Transaction> TRANSACTIONS = Arrays.asList(
            Transaction.builder()
                    .timestamp(new Date())
                    .type("Type1")
                    .actor("Actor1")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_1");
                        put("Key_2", "Data_2");
                        put("Key_3", "Data_3");
                    }})
                    .build(),
            Transaction.builder()
                    .timestamp(new Date())
                    .type("Type2")
                    .actor("Actor2")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_1");
                        put("Key_2", "Data_2");
                        put("Key_3", "Data_4");
                        put("Key_4", "Data_5");
                    }})
                    .build(),
            Transaction.builder()
                    .timestamp(new Date())
                    .type("Type3")
                    .actor("Actor3")
                    .transactionData(new HashMap<>() {{
                        put("Key_1", "Data_2");
                        put("Key_2", "Data_4");
                        put("Key_3", "Data_5");
                        put("Key_4", "Data_6");
                        put("Key_5", "Data_7");
                    }})
                    .build()
    );

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationMicroserviceAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TransactionRepository transactionRepository) {
        return args -> {
            transactionRepository.saveAll(TRANSACTIONS);
        };
    }
}
