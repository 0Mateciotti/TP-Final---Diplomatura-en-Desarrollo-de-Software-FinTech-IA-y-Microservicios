package com.mateo.product_service.dto;

import java.math.BigDecimal;

public record PrestamoRequestDTO(
        Long clienteId,
        BigDecimal montoPrestamo,
        BigDecimal tasaInteres,
        Integer cantidadCuotas,
        boolean activo
) {
}
