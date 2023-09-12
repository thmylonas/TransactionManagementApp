package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

//    @Value(value = "${jsonplaceholder.url}")
//    private String jsonplaceholderUrl;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    WebClient webClient;

    @Override
    public List<Transaction> fetchTodosByWebClient() {

        List<Transaction> todos = webClient
                .get()
                .uri("/todos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>() {
                })
                .block();

        if (todos != null) {
            transactionRepository.saveAll(todos);
        }
        return todos;
    }
}
