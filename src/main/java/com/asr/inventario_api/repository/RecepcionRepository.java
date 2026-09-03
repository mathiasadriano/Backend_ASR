package com.asr.inventario_api.repository;

import com.asr.inventario_api.model.Recepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionRepository extends JpaRepository<Recepcion, Long> {
}