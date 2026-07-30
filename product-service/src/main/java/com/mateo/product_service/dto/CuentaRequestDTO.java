package com.mateo.product_service.dto;

import com.mateo.product_service.cuenta.TipoCuenta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        @NotNull Long clienteId,
        @NotNull TipoCuenta tipoCuenta,
        @NotNull @PositiveOrZero BigDecimal saldo,
        boolean activo
) {
}
