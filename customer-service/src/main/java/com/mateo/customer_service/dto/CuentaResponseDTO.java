package com.mateo.customer_service.dto;

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
