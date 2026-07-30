package com.mateo.product_service.cuenta;

import com.mateo.product_service.dto.CuentaRequestDTO;
import com.mateo.product_service.dto.CuentaResponseDTO;
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
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    CuentaService cuentaService;

    @GetMapping
    public List<CuentaResponseDTO> getCuentas() {
        return cuentaService.getCuentas();
    }
    @GetMapping("/{id}")
    public CuentaResponseDTO getCuentaById(@PathVariable Long id) {
        return cuentaService.getCuentaById(id);
    }
    @GetMapping("/cliente/{clienteId}")
    public List<CuentaResponseDTO> getCuentasByClienteId(@PathVariable Long clienteId) {
        return cuentaService.getCuentasByClienteId(clienteId);
    }
    @PostMapping
    public CuentaResponseDTO createCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        return cuentaService.addCuenta(cuentaRequestDTO);
    }
    @PutMapping("/{id}")
    public CuentaResponseDTO updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        return cuentaService.updateCuenta(id, cuentaRequestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
