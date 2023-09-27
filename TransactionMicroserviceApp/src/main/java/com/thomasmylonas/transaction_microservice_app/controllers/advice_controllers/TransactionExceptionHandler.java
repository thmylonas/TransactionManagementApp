package com.thomasmylonas.transaction_microservice_app.controllers.advice_controllers;

import com.thomasmylonas.transaction_microservice_app.exceptions.ItemNotFoundException;
import com.thomasmylonas.transaction_microservice_app.models_dtos.response.ResponseError;
import com.thomasmylonas.transaction_microservice_app.helpers.HelperClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class TransactionExceptionHandler {

    /**
     * This default value only in DEV environment/profile. Set "false" in PROD environment/profile.
     * The client to see the stacktrace, must add in the request URI the "?trace=true"
     */
    @Value(value = "${stacktrace.print:true}")
    private boolean printStacktrace;

    @ExceptionHandler(value = {ItemNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND) // 404: "Not Found"
    public ResponseEntity<ResponseError> handleItemNotFoundException(ItemNotFoundException e, WebRequest request) {
        String message = "The resource not found: " + e.getMessage();
        return buildResponseError(e, message, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) // 400: "Bad Request"
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, WebRequest request) {
        String message = "Malformed JSON request: " + e.getMessage();
        return buildResponseError(e, message, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) // 400: "Bad Request"
    public ResponseEntity<ResponseError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        String message = "Type of path variable mismatch: " + e.getMessage();
        return buildResponseError(e, message, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500: "Internal Server Error"
    public ResponseEntity<ResponseError> handleAllUncaughtExceptions(Exception e, WebRequest request) {
        String message = "Unknown error occurred: " + e.getMessage();
        return buildResponseError(e, message, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ResponseError> buildResponseError(Exception e, String message, HttpStatus status, WebRequest request) {

        ResponseError responseError = new ResponseError(LocalDateTime.now(), status.toString(), message, request.getDescription(false));

        if (printStacktrace && isTraceParamEnabled(request)) {
            responseError.setStacktrace(HelperClass.stringifyStacktrace(e));
        }
        return ResponseEntity.status(status).body(responseError);
    }

    private boolean isTraceParamEnabled(WebRequest request) {
        String[] traceValue = request.getParameterValues("trace");
        return Objects.nonNull(traceValue) && traceValue.length > 0 && traceValue[0].equals("true");
    }
}
