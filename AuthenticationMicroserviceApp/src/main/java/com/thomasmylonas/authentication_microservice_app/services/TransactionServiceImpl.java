package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.exceptions.ItemNotFoundException;
import com.thomasmylonas.authentication_microservice_app.helpers.HelperClass;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WebClient webClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WebClient webClient) {
        this.transactionRepository = transactionRepository;
        this.webClient = webClient;
    }

    @Override
    public List<Transaction> sendTransactions(String requestUrl, List<Transaction> transactions) {

        return webClient
                .post()
                .uri(requestUrl)
                .body(BodyInserters.fromValue(transactions))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>() {
                })
                .block();
    }

    @Override
    public Transaction fetchTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));
    }

    @Override
    public List<Transaction> fetchAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> saveAllTransactions(List<Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    @Override
    public Transaction updateTransaction(Transaction newTransaction, Long id) {

        return transactionRepository.findById(id)
                .map(transaction -> {
                    if (Objects.nonNull(newTransaction.getTimestamp())) {
                        newTransaction.setTimestamp(newTransaction.getTimestamp());
                    }
                    if (Objects.nonNull(newTransaction.getType()) && !newTransaction.getType().isEmpty()) {
                        newTransaction.setType(newTransaction.getType());
                    }
                    if (Objects.nonNull(newTransaction.getActor()) && !newTransaction.getActor().isEmpty()) {
                        newTransaction.setActor(newTransaction.getActor());
                    }
                    if (Objects.nonNull(newTransaction.getTransactionData()) && !newTransaction.getTransactionData().isEmpty()) {
                        newTransaction.setTransactionData(newTransaction.getTransactionData());
                    }
                    return saveTransaction(newTransaction);
                })
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));
    }

    @Override
    public void deleteTransactionById(Long id) {

        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(Transaction.class.getSimpleName(), id);
        }
    }

    public List<Transaction> generateTransactions(int amount) {

        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            transactions.add(Transaction.builder()
                    .timestamp(HelperClass.randomTimestamp())
                    .type("Type_" + HelperClass.RANDOM.nextInt(1_000_000))
                    .actor("Actor_" + HelperClass.RANDOM.nextInt(1_000_000))
                    .transactionData(HelperClass.generateData(HelperClass.RANDOM.nextInt(10)))
                    .build());
        }
        return transactions;
    }
}
