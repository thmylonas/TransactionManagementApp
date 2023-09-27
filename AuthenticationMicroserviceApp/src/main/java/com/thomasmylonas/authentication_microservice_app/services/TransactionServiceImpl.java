package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.exceptions.ItemNotFoundException;
import com.thomasmylonas.authentication_microservice_app.models_dtos.EntityDTOMapper;
import com.thomasmylonas.authentication_microservice_app.models_dtos.dtos.TransactionDTO;
import com.thomasmylonas.authentication_microservice_app.models_dtos.response.ResponseSuccess;
import com.thomasmylonas.authentication_microservice_app.repositories.TransactionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public TransactionDTO findTransactionById(Long id) {
        Transaction order = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));
        return EntityDTOMapper.mapToDTO(order);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(EntityDTOMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAllTransactionsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TransactionDTO> transactionDTOs = transactionRepository.findAll(pageable).stream()
                .map(EntityDTOMapper::mapToDTO).collect(Collectors.toList());
        Page<TransactionDTO> transactionDTOsPage = new PageImpl<>(transactionDTOs);
        return transactionDTOsPage.getContent();
    }

    @Override
    public List<TransactionDTO> findAllTransactionsSorted(String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        return transactionRepository.findAll(sort).stream()
                .map(EntityDTOMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findAllTransactionsByPageSorted(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = retrieveSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<TransactionDTO> transactionDTOs = transactionRepository.findAll(pageable).stream()
                .map(EntityDTOMapper::mapToDTO).collect(Collectors.toList());
        Page<TransactionDTO> transactionDTOsPage = new PageImpl<>(transactionDTOs);
        return transactionDTOsPage.getContent();
    }

    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.save(EntityDTOMapper.mapToEntity(transactionDTO));
        return EntityDTOMapper.mapToDTO(transaction);
    }

    @Override
    public List<TransactionDTO> saveAllTransactions(List<TransactionDTO> transactionDTOs) {

        List<Transaction> transactions = transactionDTOs.stream()
                .map(EntityDTOMapper::mapToEntity)
                .collect(Collectors.toList());
        List<Transaction> transactionsResult = transactionRepository.saveAll(transactions);
        return transactionsResult.stream()
                .map(EntityDTOMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO updateTransaction(TransactionDTO newTransactionDTO, Long id) {

        Transaction newTransaction = EntityDTOMapper.mapToEntity(newTransactionDTO);

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
                .map(EntityDTOMapper::mapToDTO)
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));
    }

    @Override
    public TransactionDTO partialUpdateTransaction(Map<String, ?> fields, Long id) {

        Transaction transactionToUpdate = transactionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Transaction.class.getSimpleName(), id));

        fields.forEach((field, fieldValue) -> {
            Field transactionField = ReflectionUtils.findField(Transaction.class, field);
            if (transactionField != null) {
                transactionField.setAccessible(true);
                ReflectionUtils.setField(transactionField, transactionToUpdate, fieldValue);
            }
        });
        return EntityDTOMapper.mapToDTO(transactionRepository.save(transactionToUpdate));
    }

    @Override
    public void deleteTransactionById(Long id) {

        try {
            transactionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(Transaction.class.getSimpleName(), id);
        }
    }

    private Sort retrieveSort(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    }
}
