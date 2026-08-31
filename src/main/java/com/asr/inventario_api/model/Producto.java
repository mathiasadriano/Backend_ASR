package com.asr.inventario_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    private String ingredienteActivo;
    
    @Column(nullable = false)
    private String unidad;

    private BigDecimal stockActual = BigDecimal.ZERO;
    
    private BigDecimal stockMinimo = BigDecimal.ZERO;
    
    private BigDecimal costoUnitario;

    private String proveedor;
    
    private String presentacion;
    
    private String ubicacion;
    
    private LocalDate fechaVencimiento;
    
    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}