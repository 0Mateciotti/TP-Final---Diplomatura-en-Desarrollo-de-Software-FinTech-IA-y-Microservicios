package com.mateo.product_service.prestamo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoPersonal, Long> {

    List<PrestamoPersonal> findByClienteId(Long clienteId);
}
