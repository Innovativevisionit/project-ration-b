package com.sql.authentication.exception;

import com.sql.authentication.payload.response.ApiResponse;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.payload.response.Response;
import com.sql.authentication.payload.response.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        // You can customize the response body as needed
        String message = "The HTTP method " + e.getMethod() + " is not supported for this request.";
        ErrorResponse response =new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),message);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ValidationResponse errorResponse = new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String errorMessage = "Required parameter '" + ex.getParameterName() + "' is missing";
        ErrorResponse response =new ErrorResponse(HttpStatus.BAD_REQUEST.value(),errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Failed to convert value of type '" + ex.getValue().getClass().getSimpleName() + "' to required type '" + ex.getRequiredType().getSimpleName() + "'";
        if (ex.getValue() instanceof String && ((String) ex.getValue()).isEmpty()) {
            message += "; Input string is empty or missing";
        } else {
            message += "; For input string: \"" + ex.getValue() + "\"";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
