package com.mateo.customer_service.dto;

import java.util.List;

public record CustomerProfileDTO(
        CustomerResponseDTO cliente,
        List<CuentaResponseDTO> cuentas,
        List<PrestamoResponseDTO> prestamos,
        List<TarjetaResponseDTO> tarjetas
) {
}
