package com.mateo.product_service.producto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private boolean activo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaAlta;
}
