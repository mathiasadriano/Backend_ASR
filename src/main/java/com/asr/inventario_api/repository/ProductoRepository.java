package com.asr.inventario_api.repository;

import com.asr.inventario_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí luego agregaremos búsquedas personalizadas si las necesitamos (ej. buscar por categoría)
}