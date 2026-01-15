package com.thomasmylonas.transaction_microservice_app.models_dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseSuccess(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String statusCode,
        String message,
        String path, // "request URL"
        Map<String, ?> data // The resource to be returned
) {
}
