package com.thomasmylonas.transaction_microservice_app.models_dtos;

import com.thomasmylonas.transaction_microservice_app.entities.Transaction;
import com.thomasmylonas.transaction_microservice_app.models_dtos.dtos.TransactionDTO;

public class EntityDTOMapper {

    /*
    Mapping DTOs to Entities
    The DTOs come from the client-side input in the RequestBody. The entities will be given to the Repository layer
    */

    public static Transaction mapToEntity(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .timestamp(transactionDTO.getTimestamp())
                .type(transactionDTO.getType())
                .actor(transactionDTO.getActor())
                .transactionData(transactionDTO.getTransactionData())
                .build();
    }

    /*
    Mapping Entities to DTOs
    The entities come from the Repository layer. The DTOs will be given to the client-side output in the ResponseBody
    */

    public static TransactionDTO mapToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .timestamp(transaction.getTimestamp())
                .type(transaction.getType())
                .actor(transaction.getActor())
                .transactionData(transaction.getTransactionData())
                .build();
    }
}
