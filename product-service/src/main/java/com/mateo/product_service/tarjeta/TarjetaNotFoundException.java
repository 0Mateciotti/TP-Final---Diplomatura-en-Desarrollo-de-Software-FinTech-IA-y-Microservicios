package com.mateo.product_service.tarjeta;

public class TarjetaNotFoundException extends RuntimeException {

    public TarjetaNotFoundException(String message) {
        super(message);
    }
}
