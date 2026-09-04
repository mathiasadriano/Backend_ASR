package com.asr.inventario_api.controller; 

import com.asr.inventario_api.model.ReporteIngreso;
import com.asr.inventario_api.repository.ReporteIngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-ingresos")
@CrossOrigin(origins = "*") 
public class ReporteIngresoController {

    @Autowired
    private ReporteIngresoRepository repository;

    // Método para devolver la lista de reportes (GET)
    @GetMapping
    public List<ReporteIngreso> obtenerTodos() {
        return repository.findAll();
    }

    // Método para guardar un nuevo reporte (POST)
    @PostMapping
    public ResponseEntity<ReporteIngreso> crearReporte(@RequestBody ReporteIngreso reporte) {
        ReporteIngreso nuevoReporte = repository.save(reporte);
        return ResponseEntity.ok(nuevoReporte);
    }
}