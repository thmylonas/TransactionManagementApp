package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;

import java.util.List;

public interface TransactionService {

    void sendTransactions(String requestUrl, List<Transaction> transactions);

    Transaction findTransactionById(Long id);

    List<Transaction> findAllTransactions();

    List<Transaction> findAllTransactionsByPage(int pageNumber, int pageSize);

    List<Transaction> findAllTransactionsSorted(String sortBy, String sortDir);

    List<Transaction> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir);

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> saveAllTransactions(List<Transaction> transactions);

    Transaction updateTransaction(Transaction newTransaction, Long id);

    void deleteTransactionById(Long id);
}
