package com.thomasmylonas.authentication_microservice_app.controllers;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(path = "/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // http://localhost:8080/v1/transactions/{id}
    @GetMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Transaction findTransactionById(@PathVariable(value = "id") Long transactionId) {
        return transactionService.findTransactionById(transactionId);
    }

    // http://localhost:8080/v1/transactions/all
    @GetMapping(path = "/all")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    // http://localhost:8080/v1/transactions/all-by-page?page=1&size=10
    @GetMapping(path = "/all-by-page")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Transaction> findAllTransactionsByPage(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        return transactionService.findAllTransactionsByPage(pageNumber, pageSize);
    }

    // http://localhost:8080/v1/transactions/all-sorted?sort=field&dir=direction
    @GetMapping(path = "/all-sorted")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Transaction> findAllTransactionsSorted(@RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                       @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) {
        return transactionService.findAllTransactionsSorted(sortBy, sortDir);
    }

    // http://localhost:8080/v1/transactions/all-by-page-sorted?page=1&size=10&sort=field&dir=direction
    @GetMapping(path = "/all-by-page-sorted")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Transaction> findAllTransactionsByPageSorted(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                             @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize,
                                                             @RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                             @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) {
        return transactionService.findAllTransactionsByPageSorted(pageNumber, pageSize, sortBy, sortDir);
    }

    // http://localhost:8080/v1/transactions/save
    @PostMapping(path = "/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    // http://localhost:8080/v1/transactions/save-all
    @PostMapping(path = "/save-all")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Transaction> saveAllTransactions(@RequestBody List<Transaction> transactions) {
        return transactionService.saveAllTransactions(transactions);
    }

    // http://localhost:8080/v1/transactions/update/{id}
    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Transaction updateTransaction(@RequestBody Transaction newTransaction, @PathVariable(value = "id") Long transactionId) {
        return transactionService.updateTransaction(newTransaction, transactionId);
    }

    // http://localhost:8080/v1/transactions/delete/{id}
    @DeleteMapping(path = {"/delete/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteTransactionById(@PathVariable(value = "id") Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return "The transaction with ID '" + transactionId + "' has been deleted successfully!";
    }
}
