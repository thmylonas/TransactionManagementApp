package com.thomasmylonas.transaction_microservice_app.models_dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ResponseError(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String statusCode,
        String message,
        String path // "request URL"
) {
}
