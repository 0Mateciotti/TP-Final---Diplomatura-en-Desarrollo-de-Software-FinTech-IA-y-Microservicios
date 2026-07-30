package com.mateo.product_service.prestamo;

import com.mateo.product_service.dto.PrestamoRequestDTO;
import com.mateo.product_service.dto.PrestamoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoMapper prestamoMapper;

    public List<PrestamoResponseDTO> getPrestamos() {
        return prestamoRepository.findAll().stream()
                .map(prestamoMapper::toResponseDTO)
                .toList();
    }

    public PrestamoResponseDTO getPrestamoById(Long id) {
        PrestamoPersonal prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Prestamo no encontrado con id: " + id));
        return prestamoMapper.toResponseDTO(prestamo);
    }

    public List<PrestamoResponseDTO> getPrestamosByClienteId(Long clienteId) {
        return prestamoRepository.findByClienteId(clienteId).stream()
                .map(prestamoMapper::toResponseDTO)
                .toList();
    }

    public PrestamoResponseDTO addPrestamo(PrestamoRequestDTO dto) {
        PrestamoPersonal prestamo = prestamoMapper.toEntity(dto);
        return prestamoMapper.toResponseDTO(prestamoRepository.save(prestamo));
    }

    public PrestamoResponseDTO updatePrestamo(Long id, PrestamoRequestDTO dto) {
        PrestamoPersonal prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Prestamo no encontrado con id: " + id));
        prestamo.setClienteId(dto.clienteId());
        prestamo.setMontoPrestamo(dto.montoPrestamo());
        prestamo.setTasaInteres(dto.tasaInteres());
        prestamo.setCantidadCuotas(dto.cantidadCuotas());
        prestamo.setActivo(dto.activo());
        return prestamoMapper.toResponseDTO(prestamoRepository.save(prestamo));
    }

    public void deleteById(Long id) {
        prestamoRepository.deleteById(id);
    }
}
