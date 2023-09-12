package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.helpers.HardCodedTestDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    WebClient webClient;

    @Override
    public List<Transaction> generateTransactions() {
        return null;
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
}
