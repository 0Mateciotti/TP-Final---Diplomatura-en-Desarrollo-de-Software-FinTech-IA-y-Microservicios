package com.mateo.product_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TarjetaResponseDTO(
        Long id,
        Long clienteId,
        BigDecimal limite,
        boolean activo,
        LocalDateTime fechaAlta
) {
}
