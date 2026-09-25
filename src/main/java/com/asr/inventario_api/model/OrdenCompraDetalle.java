package com.asr.inventario_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ordenes_compra_detalles")
public class OrdenCompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_compra_id", nullable = false)
    @JsonIgnore 
    private OrdenCompra ordenCompra;

    @Column(precision = 10, scale = 2)
    private BigDecimal cantidad;

    private String descripcion;

    @Column(length = 50)
    private String um; 

    @Column(name = "valor_venta", precision = 12, scale = 2)
    private BigDecimal valorVenta;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;
}