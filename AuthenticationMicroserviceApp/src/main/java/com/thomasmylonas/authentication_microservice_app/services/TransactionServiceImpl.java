package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.exceptions.ItemNotFoundException;
import com.thomasmylonas.authentication_microservice_app.helpers.HelperClass;
import com.thomasmylonas.authentication_microservice_app.models.response.ResponseSuccess;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public void sendTransactions(String requestUrl, List<Transaction> transactions) {

        webClient.post()
                .uri(requestUrl)
                .body(BodyInserters.fromValue(transactions))
                .retrieve()
                .toEntity(ResponseSuccess.class)
                .block();
    }

    @Override
    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findAllTransactionsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Transaction> transactionsPage = transactionRepository.findAll(pageable);
        return transactionsPage.getContent();
    }

    @Override
    public List<Transaction> findAllTransactionsSorted(String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        return transactionRepository.findAll(sort);
    }

    @Override
    public List<Transaction> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Transaction> transactionsPage = transactionRepository.findAll(pageable);
        return transactionsPage.getContent();
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
                        transaction.setTimestamp(newTransaction.getTimestamp());
                    }
                    if (StringUtils.hasLength(newTransaction.getType())) {
                        transaction.setType(newTransaction.getType());
                    }
                    if (StringUtils.hasLength(newTransaction.getActor())) {
                        transaction.setActor(newTransaction.getActor());
                    }
                    if (Objects.nonNull(newTransaction.getTransactionData()) && !newTransaction.getTransactionData().isEmpty()) {
                        transaction.setTransactionData(newTransaction.getTransactionData());
                    }
                    return transactionRepository.save(transaction);
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

    private Sort retrieveSort(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    }
}
