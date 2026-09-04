package com.asr.inventario.repository; 

import com.tuempresa.inventario.model.ReporteIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteIngresoRepository extends JpaRepository<ReporteIngreso, Long> {
}