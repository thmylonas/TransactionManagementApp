package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.models_dtos.dtos.TransactionDTO;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    void sendTransactions(String requestUrl, List<Transaction> transactions);

    TransactionDTO findTransactionById(Long id);

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findAllTransactionsByPage(int pageNumber, int pageSize);

    List<TransactionDTO> findAllTransactionsSorted(String sortBy, String sortDir);

    List<TransactionDTO> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir);

    TransactionDTO saveTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> saveAllTransactions(List<TransactionDTO> TransactionDTOs);

    TransactionDTO updateTransaction(TransactionDTO newTransactionDTO, Long id);

    TransactionDTO partialUpdateTransaction(Map<String, ?> fields, Long id);

    void deleteTransactionById(Long id);
}
