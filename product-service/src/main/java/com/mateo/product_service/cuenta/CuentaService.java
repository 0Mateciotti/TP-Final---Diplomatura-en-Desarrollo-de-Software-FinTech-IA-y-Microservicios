package com.mateo.product_service.cuenta;

import com.mateo.product_service.dto.CuentaRequestDTO;
import com.mateo.product_service.dto.CuentaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    public List<CuentaResponseDTO> getCuentas() {
        return cuentaRepository.findAll().stream()
                .map(cuentaMapper::toResponseDTO)
                .toList();
    }

    public CuentaResponseDTO getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con id: " + id));
        return cuentaMapper.toResponseDTO(cuenta);
    }

    public List<CuentaResponseDTO> getCuentasByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId).stream()
                .map(cuentaMapper::toResponseDTO)
                .toList();
    }

    public CuentaResponseDTO addCuenta(CuentaRequestDTO dto) {
        Cuenta cuenta = cuentaMapper.toEntity(dto);
        return cuentaMapper.toResponseDTO(cuentaRepository.save(cuenta));
    }

    public CuentaResponseDTO updateCuenta(Long id, CuentaRequestDTO dto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con id: " + id));
        cuenta.setClienteId(dto.clienteId());
        cuenta.setTipoCuenta(dto.tipoCuenta());
        cuenta.setSaldo(dto.saldo());
        cuenta.setActivo(dto.activo());
        return cuentaMapper.toResponseDTO(cuentaRepository.save(cuenta));
    }

    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }
}
