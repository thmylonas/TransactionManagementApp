package com.thomasmylonas.transaction_microservice_app.services;

import com.thomasmylonas.transaction_microservice_app.entities.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction findTransactionById(Long id);

    List<Transaction> findAllTransactions();

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> saveAllTransactions(List<Transaction> transactions);

    Transaction updateTransaction(Transaction newTransaction, Long id);

    void deleteTransactionById(Long id);
}
