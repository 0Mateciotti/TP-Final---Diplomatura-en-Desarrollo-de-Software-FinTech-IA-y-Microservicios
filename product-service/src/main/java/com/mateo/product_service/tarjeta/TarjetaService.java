package com.mateo.product_service.tarjeta;

import com.mateo.product_service.dto.TarjetaRequestDTO;
import com.mateo.product_service.dto.TarjetaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TarjetaMapper tarjetaMapper;

    public List<TarjetaResponseDTO> getTarjetas() {
        return tarjetaRepository.findAll().stream()
                .map(tarjetaMapper::toResponseDTO)
                .toList();
    }

    public TarjetaResponseDTO getTarjetaById(Long id) {
        TarjetaCredito tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNotFoundException("Tarjeta no encontrada con id: " + id));
        return tarjetaMapper.toResponseDTO(tarjeta);
    }

    public List<TarjetaResponseDTO> getTarjetasByClienteId(Long clienteId) {
        return tarjetaRepository.findByClienteId(clienteId).stream()
                .map(tarjetaMapper::toResponseDTO)
                .toList();
    }

    public TarjetaResponseDTO addTarjeta(TarjetaRequestDTO dto) {
        TarjetaCredito tarjeta = tarjetaMapper.toEntity(dto);
        return tarjetaMapper.toResponseDTO(tarjetaRepository.save(tarjeta));
    }

    public TarjetaResponseDTO updateTarjeta(Long id, TarjetaRequestDTO dto) {
        TarjetaCredito tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNotFoundException("Tarjeta no encontrada con id: " + id));
        tarjeta.setClienteId(dto.clienteId());
        tarjeta.setLimite(dto.limite());
        tarjeta.setActivo(dto.activo());
        return tarjetaMapper.toResponseDTO(tarjetaRepository.save(tarjeta));
    }

    public void deleteById(Long id) {
        tarjetaRepository.deleteById(id);
    }
}
