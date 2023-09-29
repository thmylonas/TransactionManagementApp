package com.thomasmylonas.authentication_microservice_app.models_dtos.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This DTO will be used for both RequestBody/ResponseBody data
 * In case of ResponseBody, the getters are mandatory, so Jackson can serialize the DTO to JSON
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;
    private LocalDateTime timestamp;
    private String type;
    private String actor;
    private Map<String, String> transactionData;
}
