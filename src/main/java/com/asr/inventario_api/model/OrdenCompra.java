package com.asr.inventario_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_oc", unique = true)
    private String numeroOc;

    @Column(name = "solicitado_por")
    private String solicitadoPor;

    private LocalDate fecha;

    @Column(name = "proveedor_razon_social")
    private String proveedorRazonSocial;

    @Column(name = "proveedor_contacto")
    private String proveedorContacto;

    @Column(name = "tipo_bien_servicio")
    private String tipoBienServicio; // "BIEN" o "SERVICIO"

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "sub_total", precision = 12, scale = 2)
    private BigDecimal subTotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal igv;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenCompraDetalle> detalles = new ArrayList<>();

    public void addDetalle(OrdenCompraDetalle detalle) {
        detalles.add(detalle);
        detalle.setOrdenCompra(this);
    }
}