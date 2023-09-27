package com.thomasmylonas.transaction_microservice_app.models_dtos;

import com.thomasmylonas.transaction_microservice_app.models_dtos.response.ResponseSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<ResponseSuccess> buildResponse(String message, HttpStatus status, Map<String, ?> data) {
        return buildResponse(message, status, ServletUriComponentsBuilder.fromCurrentRequest().toUriString(), data);
    }

    public static ResponseEntity<ResponseSuccess> buildResponse(String message, HttpStatus status, String path, Map<String, ?> data) {

        ResponseSuccess responseSuccess = ResponseSuccess.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(status.toString())
                .message(message)
                .path(path)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(responseSuccess);
    }
}
