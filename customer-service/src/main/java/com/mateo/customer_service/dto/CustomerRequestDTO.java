package com.mateo.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank String documento,
        @NotBlank String direccion,
        @NotBlank @Email String email
) {
}
