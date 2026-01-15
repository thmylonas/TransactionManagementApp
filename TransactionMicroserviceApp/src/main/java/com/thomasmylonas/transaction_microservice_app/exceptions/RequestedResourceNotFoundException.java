package com.thomasmylonas.transaction_microservice_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404: "Not Found"
public class RequestedResourceNotFoundException extends RuntimeException {

    public RequestedResourceNotFoundException(String clazz, Long resourceId) {
        super(String.format("%s: The %s with the ID %d is not found!",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")), clazz, resourceId));
    }
}
