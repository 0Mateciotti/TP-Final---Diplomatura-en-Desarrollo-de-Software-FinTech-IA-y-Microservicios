package com.mateo.product_service.cuenta;

import com.mateo.product_service.producto.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Cuenta extends Producto {

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    private BigDecimal saldo;
}
