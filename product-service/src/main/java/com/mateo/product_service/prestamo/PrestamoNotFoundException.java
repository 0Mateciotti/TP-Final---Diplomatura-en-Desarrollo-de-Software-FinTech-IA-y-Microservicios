package com.mateo.product_service.prestamo;

public class PrestamoNotFoundException extends RuntimeException {

    public PrestamoNotFoundException(String message) {
        super(message);
    }
}
