package com.mateo.product_service.tarjeta;

import com.mateo.product_service.dto.TarjetaRequestDTO;
import com.mateo.product_service.dto.TarjetaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {
    @Autowired
    TarjetaService tarjetaService;

    @GetMapping
    public List<TarjetaResponseDTO> getTarjetas() {
        return tarjetaService.getTarjetas();
    }
    @GetMapping("/{id}")
    public TarjetaResponseDTO getTarjetaById(@PathVariable Long id) {
        return tarjetaService.getTarjetaById(id);
    }
    @GetMapping("/cliente/{clienteId}")
    public List<TarjetaResponseDTO> getTarjetasByClienteId(@PathVariable Long clienteId) {
        return tarjetaService.getTarjetasByClienteId(clienteId);
    }
    @PostMapping
    public TarjetaResponseDTO createTarjeta(@Valid @RequestBody TarjetaRequestDTO tarjetaRequestDTO) {
        return tarjetaService.addTarjeta(tarjetaRequestDTO);
    }
    @PutMapping("/{id}")
    public TarjetaResponseDTO updateTarjeta(@PathVariable Long id, @Valid @RequestBody TarjetaRequestDTO tarjetaRequestDTO) {
        return tarjetaService.updateTarjeta(id, tarjetaRequestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        tarjetaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
