package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> sendTransactions(List<Transaction> transactions);
}
