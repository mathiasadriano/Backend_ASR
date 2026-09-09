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

    @GetMapping
    public List<ReporteIngreso> obtenerTodos() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<ReporteIngreso> crearReporte(@RequestBody ReporteIngreso reporte) {
        ReporteIngreso nuevoReporte = repository.save(reporte);
        return ResponseEntity.ok(nuevoReporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIngreso(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Registro eliminado correctamente");
        }
        return ResponseEntity.status(404).body("Registro no encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteIngreso> actualizarIngreso(@PathVariable Long id, @RequestBody ReporteIngreso datosActualizados) {
        return repository.findById(id)
            .map(registroExistente -> {
                registroExistente.setInsumo(datosActualizados.getInsumo());
                registroExistente.setCantidad(datosActualizados.getCantidad());
                registroExistente.setNumeroPedido(datosActualizados.getNumeroPedido());
                registroExistente.setGuiaFactura(datosActualizados.getGuiaFactura());
                // Ajusta 'getSolicitante' al nombre real de tu campo si en tu BD es 'Proveedor'
                registroExistente.setSolicitante(datosActualizados.getSolicitante()); 
                registroExistente.setFecha(datosActualizados.getFecha());
                
                return ResponseEntity.ok(repository.save(registroExistente));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}