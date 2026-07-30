package com.mateo.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record PrestamoRequestDTO(
        @NotNull Long clienteId,
        @NotNull @Positive BigDecimal montoPrestamo,
        @NotNull @PositiveOrZero BigDecimal tasaInteres,
        @NotNull @Positive Integer cantidadCuotas,
        boolean activo
) {
}
