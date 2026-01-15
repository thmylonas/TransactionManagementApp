package com.thomasmylonas.transaction_microservice_app.services;

import com.thomasmylonas.transaction_microservice_app.models_dtos.dtos.TransactionDto;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    TransactionDto findTransactionById(Long id);

    List<TransactionDto> findAllTransactions();

    List<TransactionDto> findAllTransactionsByPage(int pageNumber, int pageSize);

    List<TransactionDto> findAllTransactionsSorted(String sortBy, String sortDir);

    List<TransactionDto> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir);

    TransactionDto saveTransaction(TransactionDto transactionDto);

    List<TransactionDto> saveAllTransactions(List<TransactionDto> transactionDtos);

    TransactionDto updateTransaction(TransactionDto newTransactionDto, Long id);

    TransactionDto partialUpdateTransaction(Map<String, ?> fields, Long id);

    void deleteTransactionById(Long id);
}
