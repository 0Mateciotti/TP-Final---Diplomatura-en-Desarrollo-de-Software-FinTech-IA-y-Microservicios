package com.mateo.product_service.prestamo;

import com.mateo.product_service.dto.PrestamoRequestDTO;
import com.mateo.product_service.dto.PrestamoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/prestamos")
public class PrestamoController {
    @Autowired
    PrestamoService prestamoService;

    @GetMapping
    public List<PrestamoResponseDTO> getPrestamos() {
        return prestamoService.getPrestamos();
    }
    @GetMapping("/{id}")
    public PrestamoResponseDTO getPrestamoById(@PathVariable Long id) {
        return prestamoService.getPrestamoById(id);
    }
    @GetMapping("/cliente/{clienteId}")
    public List<PrestamoResponseDTO> getPrestamosByClienteId(@PathVariable Long clienteId) {
        return prestamoService.getPrestamosByClienteId(clienteId);
    }
    @PostMapping
    public PrestamoResponseDTO createPrestamo(@RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        return prestamoService.addPrestamo(prestamoRequestDTO);
    }
    @PutMapping("/{id}")
    public PrestamoResponseDTO updatePrestamo(@PathVariable Long id, @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        return prestamoService.updatePrestamo(id, prestamoRequestDTO);
    }
    @DeleteMapping("/{id}")
    public void deletePrestamo(@PathVariable Long id) {
        prestamoService.deleteById(id);
    }
}
