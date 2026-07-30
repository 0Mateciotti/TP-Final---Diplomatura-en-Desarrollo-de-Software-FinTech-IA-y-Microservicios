package com.mateo.customer_service.dto;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String documento,
        String direccion,
        String email,
        LocalDateTime createdAt
) {
}
