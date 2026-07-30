package com.mateo.product_service.exception;

import com.mateo.product_service.cuenta.CuentaNotFoundException;
import com.mateo.product_service.prestamo.PrestamoNotFoundException;
import com.mateo.product_service.tarjeta.TarjetaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CuentaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCuentaNotFound(CuentaNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(PrestamoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePrestamoNotFound(PrestamoNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(TarjetaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTarjetaNotFound(TarjetaNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

    private ResponseEntity<ErrorResponse> notFound(String message) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
