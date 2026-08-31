package com.asr.inventario_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id", nullable = false)
    private Long productoId; 

    @Column(nullable = false)
    private String tipo; 

    @Column(nullable = false)
    private BigDecimal cantidad;

    private String motivo;
    private String responsable;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    @Column(updatable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}