package com.thomasmylonas.transaction_microservice_app.models_dtos.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This DTO will be used for both RequestBody/ResponseBody data
 * In case of ResponseBody, the getters are mandatory, so Jackson can serialize the DTO to JSON
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionDto(
        Long id,
        LocalDateTime timestamp,
        String type,
        String actor,
        Map<String, String> transactionData
) {
}
