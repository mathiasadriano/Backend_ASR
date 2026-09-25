package com.asr.inventario_api.repository;

import com.asr.inventario_api.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    // Aquí luego agregaremos búsquedas por proveedor o rango de fechas si lo necesitas
}