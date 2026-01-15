package com.thomasmylonas.transaction_microservice_app.services;

import com.thomasmylonas.transaction_microservice_app.entities.Transaction;
import com.thomasmylonas.transaction_microservice_app.exceptions.RequestedResourceNotFoundException;
import com.thomasmylonas.transaction_microservice_app.models_dtos.dtos.TransactionDto;
import com.thomasmylonas.transaction_microservice_app.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service(value = "transactionService")
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDto findTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RequestedResourceNotFoundException(Transaction.class.getSimpleName(), id));
        return transactionMapper.fromTransaction(transaction);
    }

    @Override
    public List<TransactionDto> findAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::fromTransaction).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findAllTransactionsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TransactionDto> transactionDtos = transactionRepository.findAll(pageable).stream()
                .map(transactionMapper::fromTransaction).collect(Collectors.toList());
        Page<TransactionDto> transactionDtosPage = new PageImpl<>(transactionDtos);
        return transactionDtosPage.getContent();
    }

    @Override
    public List<TransactionDto> findAllTransactionsSorted(String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        return transactionRepository.findAll(sort).stream()
                .map(transactionMapper::fromTransaction).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<TransactionDto> transactionDtos = transactionRepository.findAll(pageable).stream()
                .map(transactionMapper::fromTransaction).collect(Collectors.toList());
        Page<TransactionDto> transactionDtosPage = new PageImpl<>(transactionDtos);
        return transactionDtosPage.getContent();
    }

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Transaction savedTransaction = transactionRepository.save(transactionMapper.toTransaction(transactionDto));
        return transactionMapper.fromTransaction(savedTransaction);
    }

    @Override
    public List<TransactionDto> saveAllTransactions(List<TransactionDto> transactionDtos) {

        List<Transaction> transactions = transactionDtos.stream()
                .map(transactionMapper::toTransaction)
                .collect(Collectors.toList());
        List<Transaction> savedTransactions = transactionRepository.saveAll(transactions);
        return savedTransactions.stream()
                .map(transactionMapper::fromTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto newTransactionDto, Long id) {

        Transaction newTransaction = transactionMapper.toTransaction(newTransactionDto);

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
                .map(transactionMapper::fromTransaction)
                .orElseThrow(() -> new RequestedResourceNotFoundException(Transaction.class.getSimpleName(), id));
    }

    @Override
    public TransactionDto partialUpdateTransaction(Map<String, ?> fields, Long id) {

        Transaction transactionToUpdate = transactionRepository.findById(id)
                .orElseThrow(() -> new RequestedResourceNotFoundException(Transaction.class.getSimpleName(), id));

        fields.forEach((field, fieldValue) -> {
            Field transactionField = ReflectionUtils.findField(Transaction.class, field);
            if (transactionField != null) {
                transactionField.setAccessible(true);
                ReflectionUtils.setField(transactionField, transactionToUpdate, fieldValue);
            }
        });
        return transactionMapper.fromTransaction(transactionRepository.save(transactionToUpdate));
    }

    @Override
    public void deleteTransactionById(Long id) {

        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RequestedResourceNotFoundException(Transaction.class.getSimpleName(), id);
        }
    }

    private Sort retrieveSort(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    }
}
