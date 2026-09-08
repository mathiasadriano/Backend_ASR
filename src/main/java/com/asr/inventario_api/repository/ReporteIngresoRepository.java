package com.asr.inventario_api.repository; 

import com.asr.inventario_api.model.ReporteIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteIngresoRepository extends JpaRepository<ReporteIngreso, Long> {
    
    List<ReporteIngreso> findByFecha(LocalDate fecha);
}