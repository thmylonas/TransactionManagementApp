package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

    private final Random random = new Random();

    @Autowired
    WebClient webClient;

    public List<Transaction> generateTransactions(int amount) {

        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            transactions.add(Transaction.builder()
                    .timestamp(randomTimestamp())
                    .type("Type_" + random.nextInt(1_000_000))
                    .actor("Actor_" + random.nextInt(1_000_000))
                    .transactionData(generateData(random.nextInt(10)))
                    .build());
        }
        return transactions;
    }

    @Override
    public List<Transaction> sendTransactions(List<Transaction> transactions) {

        return webClient
                .post()
                .uri("http://localhost:8081/transactions/save-all")
                .body(BodyInserters.fromValue(HardCodedTestDataProvider.TRANSACTIONS_LIST_HARD_CODED))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>() {
                })
                .block();
    }

    private Timestamp randomTimestamp() {
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2014-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long) (Math.random() * diff));
    }

    private Map<String, String> generateData(int dataAmount) {

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < dataAmount; i++) {
            map.put("Key_" + random.nextInt(1_000_000), "Data_" + random.nextInt(1_000_000));
        }
        return map;
    }
}
