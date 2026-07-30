package com.mateo.product_service.dto;

import java.math.BigDecimal;

public record TarjetaRequestDTO(
        Long clienteId,
        BigDecimal limite,
        boolean activo
) {
}
