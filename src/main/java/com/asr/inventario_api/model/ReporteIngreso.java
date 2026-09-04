package com.asr.inventario_api.model; 

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reportes_ingresos")
public class ReporteIngreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String insumo;
    private Double cantidad;
    private String numeroPedido;
    private String solicitante;
    private LocalDate fecha;

    // Constructores
    public ReporteIngreso() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInsumo() { return insumo; }
    public void setInsumo(String insumo) { this.insumo = insumo; }

    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) { this.solicitante = solicitante; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}