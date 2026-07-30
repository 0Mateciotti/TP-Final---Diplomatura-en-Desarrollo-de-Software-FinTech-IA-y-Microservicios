package com.mateo.product_service.tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<TarjetaCredito, Long> {

    List<TarjetaCredito> findByClienteId(Long clienteId);
}
