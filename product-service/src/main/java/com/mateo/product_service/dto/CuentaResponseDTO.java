package com.mateo.product_service.dto;

import com.mateo.product_service.cuenta.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CuentaResponseDTO(
        Long id,
        Long clienteId,
        TipoCuenta tipoCuenta,
        BigDecimal saldo,
        boolean activo,
        LocalDateTime fechaAlta
) {
}
