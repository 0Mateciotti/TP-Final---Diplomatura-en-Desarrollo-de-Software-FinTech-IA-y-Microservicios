package com.mateo.product_service.prestamo;

import com.mateo.product_service.dto.PrestamoRequestDTO;
import com.mateo.product_service.dto.PrestamoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {

    public PrestamoPersonal toEntity(PrestamoRequestDTO dto) {
        PrestamoPersonal prestamo = new PrestamoPersonal();
        prestamo.setClienteId(dto.clienteId());
        prestamo.setMontoPrestamo(dto.montoPrestamo());
        prestamo.setTasaInteres(dto.tasaInteres());
        prestamo.setCantidadCuotas(dto.cantidadCuotas());
        prestamo.setActivo(dto.activo());
        return prestamo;
    }

    public PrestamoResponseDTO toResponseDTO(PrestamoPersonal prestamo) {
        return new PrestamoResponseDTO(
                prestamo.getId(),
                prestamo.getClienteId(),
                prestamo.getMontoPrestamo(),
                prestamo.getTasaInteres(),
                prestamo.getCantidadCuotas(),
                prestamo.isActivo(),
                prestamo.getFechaAlta()
        );
    }
}
