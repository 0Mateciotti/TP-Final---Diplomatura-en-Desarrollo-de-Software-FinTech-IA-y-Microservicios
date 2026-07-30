package com.mateo.customer_service.dto;

public record CustomerRequestDTO(
        String nombre,
        String apellido,
        String documento,
        String direccion,
        String email
) {
}
