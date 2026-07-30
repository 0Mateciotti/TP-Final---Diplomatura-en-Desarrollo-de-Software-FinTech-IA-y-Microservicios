package com.mateo.product_service.cuenta;

import com.mateo.product_service.dto.CuentaRequestDTO;
import com.mateo.product_service.dto.CuentaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toEntity(CuentaRequestDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId(dto.clienteId());
        cuenta.setTipoCuenta(dto.tipoCuenta());
        cuenta.setSaldo(dto.saldo());
        cuenta.setActivo(dto.activo());
        return cuenta;
    }

    public CuentaResponseDTO toResponseDTO(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getId(),
                cuenta.getClienteId(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldo(),
                cuenta.isActivo(),
                cuenta.getFechaAlta()
        );
    }
}
