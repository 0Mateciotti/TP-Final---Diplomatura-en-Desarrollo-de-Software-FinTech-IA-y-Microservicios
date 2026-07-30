package com.mateo.product_service.cuenta;

public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException(String message) {
        super(message);
    }
}
