package com.mateo.product_service.tarjeta;

import com.mateo.product_service.dto.TarjetaRequestDTO;
import com.mateo.product_service.dto.TarjetaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TarjetaMapper {

    public TarjetaCredito toEntity(TarjetaRequestDTO dto) {
        TarjetaCredito tarjeta = new TarjetaCredito();
        tarjeta.setClienteId(dto.clienteId());
        tarjeta.setLimite(dto.limite());
        tarjeta.setActivo(dto.activo());
        return tarjeta;
    }

    public TarjetaResponseDTO toResponseDTO(TarjetaCredito tarjeta) {
        return new TarjetaResponseDTO(
                tarjeta.getId(),
                tarjeta.getClienteId(),
                tarjeta.getLimite(),
                tarjeta.isActivo(),
                tarjeta.getFechaAlta()
        );
    }
}
