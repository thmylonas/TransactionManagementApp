package com.thomasmylonas.authentication_microservice_app.controllers;

import com.thomasmylonas.authentication_microservice_app.models_dtos.ResponseHandler;
import com.thomasmylonas.authentication_microservice_app.models_dtos.dtos.TransactionDto;
import com.thomasmylonas.authentication_microservice_app.models_dtos.response.ResponseSuccess;
import com.thomasmylonas.authentication_microservice_app.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(path = {"/api/v1/transactions"})
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(path = {"/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findTransactionById(@PathVariable(value = "id") Long transactionId) { // "http://localhost:8080/api/v1/transactions/{id}"
        TransactionDto transactionDto = transactionService.findTransactionById(transactionId);
        final String message = String.format("Success: The transaction with the ID %d is found!", transactionId);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transaction", transactionDto));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactions() { // "http://localhost:8080/api/v1/transactions"
        final String message = "Success: The transactions are found!";
        List<TransactionDto> transactionDtos = transactionService.findAllTransactions();
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactionDtos));
    }

    @GetMapping(params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsByPage(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) { // "http://localhost:8080/api/v1/transactions?page=1&size=10"
        final String message = "Success: The transactions by-page are found!";
        List<TransactionDto> transactionDtos = transactionService.findAllTransactionsByPage(pageNumber, pageSize);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactionDtos));
    }

    @GetMapping(params = {"sort", "dir"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsSorted(@RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                                     @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) { // "http://localhost:8080/api/v1/transactions?sort=field&dir=direction"
        final String message = "Success: The transactions sorted are found!";
        List<TransactionDto> transactionDtos = transactionService.findAllTransactionsSorted(sortBy, sortDir);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactionDtos));
    }

    @GetMapping(params = {"page", "size", "sort", "dir"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> findAllTransactionsByPageSorted(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize,
                                                                           @RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                                           @RequestParam(value = "dir", defaultValue = "asc", required = false) String sortDir) { // "http://localhost:8080/api/v1/transactions?page=1&size=10&sort=field&dir=direction"
        final String message = "Success: The transactions by-page-sorted are found!";
        List<TransactionDto> transactionDtos = transactionService.findAllTransactionsByPageSorted(pageNumber, pageSize, sortBy, sortDir);
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transactions", transactionDtos));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ResponseSuccess> saveTransaction(@RequestBody TransactionDto transactionDto) { // "http://localhost:8080/api/v1/transactions"
        TransactionDto savedTransactionDto = transactionService.saveTransaction(transactionDto);
        final String message = "Created: The transaction has been created successfully!";
        final String savedTransactionUri = "/v1/transactions/" + savedTransactionDto.id();
        return ResponseHandler.buildResponse(message, HttpStatus.CREATED, savedTransactionUri, Map.of("transaction", savedTransactionDto));
    }

    @PostMapping(path = {"/all"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ResponseSuccess> saveAllTransactions(@RequestBody List<TransactionDto> transactionDtos) { // "http://localhost:8080/api/v1/transactions/all"
        List<TransactionDto> savedTransactionDtos = transactionService.saveAllTransactions(transactionDtos);
        final String message = "Created: The transactions have been created successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.CREATED, Map.of("transactions", savedTransactionDtos));
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> updateTransaction(@RequestBody TransactionDto newTransactionDto, @PathVariable(value = "id") Long transactionId) { // "http://localhost:8080/api/v1/transactions/{id}"
        TransactionDto updatedTransactionDto = transactionService.updateTransaction(newTransactionDto, transactionId);
        final String message = "Success: The transaction with ID " + transactionId + " has been updated successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transaction", updatedTransactionDto));
    }

    @PatchMapping(path = {"/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> partialUpdateTransaction(@RequestBody Map<String, ?> fields, @PathVariable("id") Long transactionId) { // "http://localhost:8080/api/v1/transactions/{id}"
        TransactionDto updatedTransactionDto = transactionService.partialUpdateTransaction(fields, transactionId);
        final String message = "Success: The transaction with ID " + transactionId + " has been partially updated successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("transaction", updatedTransactionDto));
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseSuccess> deleteTransactionById(@PathVariable(value = "id") Long transactionId) { // "http://localhost:8080/api/v1/transactions/{id}"
        transactionService.deleteTransactionById(transactionId);
        final String message = "Success: The transaction with ID " + transactionId + " has been deleted successfully!";
        return ResponseHandler.buildResponse(message, HttpStatus.OK, Map.of("message", message));
    }
}
