package com.mateo.product_service.tarjeta;

import com.mateo.product_service.producto.Producto;
import jakarta.persistence.Entity;
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
public class TarjetaCredito extends Producto {

    private BigDecimal limite;
}
