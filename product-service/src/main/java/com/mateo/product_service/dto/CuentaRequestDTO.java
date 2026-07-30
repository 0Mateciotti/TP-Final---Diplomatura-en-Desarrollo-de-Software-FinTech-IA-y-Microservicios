package com.mateo.product_service.dto;

import com.mateo.product_service.cuenta.TipoCuenta;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        Long clienteId,
        TipoCuenta tipoCuenta,
        BigDecimal saldo,
        boolean activo
) {
}
