package com.thomasmylonas.authentication_microservice_app.services;

import com.thomasmylonas.authentication_microservice_app.entities.Transaction;
import com.thomasmylonas.authentication_microservice_app.models_dtos.dtos.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    /*
    Mapping DTOs to Entities
    The DTOs come from the client-side input in the RequestBody. The entities will be given to the Repository layer
    */

    public Transaction toTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .timestamp(transactionDto.timestamp())
                .type(transactionDto.type())
                .actor(transactionDto.actor())
                .transactionData(transactionDto.transactionData())
                .build();
    }

    /*
    Mapping Entities to DTOs
    The entities come from the Repository layer. The DTOs will be given to the client-side output in the ResponseBody
    */

    public TransactionDto fromTransaction(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .timestamp(transaction.getTimestamp())
                .type(transaction.getType())
                .actor(transaction.getActor())
                .transactionData(transaction.getTransactionData())
                .build();
    }
}
