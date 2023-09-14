package com.thomasmylonas.transaction_microservice_app.services;

import com.thomasmylonas.transaction_microservice_app.entities.Transaction;
import com.thomasmylonas.transaction_microservice_app.exceptions.ItemNotFoundException;
import com.thomasmylonas.transaction_microservice_app.repositories.TransactionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
