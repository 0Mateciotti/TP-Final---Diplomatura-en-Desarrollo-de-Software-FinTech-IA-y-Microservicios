package com.mateo.customer_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PrestamoResponseDTO(
        Long id,
        Long clienteId,
        BigDecimal montoPrestamo,
        BigDecimal tasaInteres,
        Integer cantidadCuotas,
        boolean activo,
        LocalDateTime fechaAlta
) {
}
