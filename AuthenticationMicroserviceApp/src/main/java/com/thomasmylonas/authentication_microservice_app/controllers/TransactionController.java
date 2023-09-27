package com.thomasmylonas.authentication_microservice_app.controllers;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.models_dtos.ResponseHandler;
import com.thomasmylonas.authentication_microservice_app.models_dtos.response.ResponseSuccess;
import com.thomasmylonas.authentication_microservice_app.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // http://localhost:8080/v1/transactions/{id}
    @GetMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findTransactionById(@PathVariable(value = "id") Long transactionId) {
        Transaction transaction = transactionService.findTransactionById(transactionId);
        final String message = String.format("Success: The transaction with the ID %d is found!", transactionId);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transaction", transaction));
    }

    // http://localhost:8080/v1/transactions/all
    @GetMapping(path = "/all")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactions() {
        final String message = "Success: The transactions are found!";
        List<Transaction> transactions = transactionService.findAllTransactions();
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactions));

    }

    // http://localhost:8080/v1/transactions/all-by-page?page=1&size=10
    @GetMapping(path = "/all-by-page")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsByPage(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        final String message = "Success: The transactions by-page are found!";
        List<Transaction> transactions = transactionService.findAllTransactionsByPage(pageNumber, pageSize);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactions));
    }

    // http://localhost:8080/v1/transactions/all-sorted?sort=field&dir=direction
    @GetMapping(path = "/all-sorted")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsSorted(@RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                                     @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) {
        final String message = "Success: The transactions sorted are found!";
        List<Transaction> transactions = transactionService.findAllTransactionsSorted(sortBy, sortDir);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactions));
    }

    // http://localhost:8080/v1/transactions/all-by-page-sorted?page=1&size=10&sort=field&dir=direction
    @GetMapping(path = "/all-by-page-sorted")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsByPageSorted(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize,
                                                                           @RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                                           @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) {
        final String message = "Success: The transactions by-page-sorted are found!";
        List<Transaction> transactions = transactionService.findAllTransactionsByPageSorted(pageNumber, pageSize, sortBy, sortDir);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactions));
    }

    // http://localhost:8080/v1/transactions/save
    @PostMapping(path = "/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ResponseSuccess> saveTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        final String message = "Created: The transaction has been created successfully!";
        final String savedTransactionUri = "/v1/transactions/" + savedTransaction.getId();
        return ResponseHandler.buildResponse(message, HttpStatus.CREATED, savedTransactionUri, Map.of("transaction", savedTransaction));
    }

    // http://localhost:8080/v1/transactions/save-all
    @PostMapping(path = "/save-all")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ResponseSuccess> saveAllTransactions(@RequestBody List<Transaction> transactions) {
        List<Transaction> savedTransactions = transactionService.saveAllTransactions(transactions);
        final String message = "Created: The transactions have been created successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.CREATED, Map.of("transactions", savedTransactions));
    }

    // http://localhost:8080/v1/transactions/update/{id}
    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> updateTransaction(@RequestBody Transaction newTransaction, @PathVariable(value = "id") Long transactionId) {
        Transaction updatedTransaction = transactionService.updateTransaction(newTransaction, transactionId);
        final String message = "Success: The transaction with ID " + transactionId + " has been updated successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transaction", updatedTransaction));
    }

    // http://localhost:8080/v1/transactions/delete/{id}
    @DeleteMapping(path = {"/delete/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> deleteTransactionById(@PathVariable(value = "id") Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
        final String message = "Success: The transaction with ID " + transactionId + " has been deleted successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("message", message));
    }
}
