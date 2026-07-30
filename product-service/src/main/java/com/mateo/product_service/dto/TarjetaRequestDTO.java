package com.mateo.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TarjetaRequestDTO(
        @NotNull Long clienteId,
        @NotNull @Positive BigDecimal limite,
        boolean activo
) {
}
