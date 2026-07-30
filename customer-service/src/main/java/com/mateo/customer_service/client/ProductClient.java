package com.mateo.customer_service.client;

import com.mateo.customer_service.dto.CuentaResponseDTO;
import com.mateo.customer_service.dto.PrestamoResponseDTO;
import com.mateo.customer_service.dto.TarjetaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/cuentas/cliente/{clienteId}")
    List<CuentaResponseDTO> getCuentasByClienteId(@PathVariable("clienteId") Long clienteId);

    @GetMapping("/prestamos/cliente/{clienteId}")
    List<PrestamoResponseDTO> getPrestamosByClienteId(@PathVariable("clienteId") Long clienteId);

    @GetMapping("/tarjetas/cliente/{clienteId}")
    List<TarjetaResponseDTO> getTarjetasByClienteId(@PathVariable("clienteId") Long clienteId);
}
