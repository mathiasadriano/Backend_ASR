package com.asr.inventario_api.controller;

import com.asr.inventario_api.model.Producto;
import com.asr.inventario_api.model.Recepcion;
import com.asr.inventario_api.service.RecepcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recepciones")
@CrossOrigin(origins = "http://localhost:5173/")
public class RecepcionController {

    @Autowired
    private RecepcionService recepcionService;

    // DTO (Molde) para atrapar el JSON
    public static class RecepcionRequest {
        private Producto producto;
        private Recepcion recepcion;

        public Producto getProducto() { return producto; }
        public void setProducto(Producto producto) { this.producto = producto; }
        
        public Recepcion getRecepcion() { return recepcion; }
        public void setRecepcion(Recepcion recepcion) { this.recepcion = recepcion; }
    }

    @PostMapping
    public ResponseEntity<Recepcion> registrarRecepcion(@RequestBody RecepcionRequest request) {
        try {
            Recepcion nuevaRecepcion = recepcionService.registrarValidacion(
                    request.getRecepcion(), 
                    request.getProducto()
            );
            // Forma moderna y clara (Evita la ambigüedad)
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRecepcion);
        } catch (Exception e) {
            e.printStackTrace(); 
            // Forma moderna para retornar un error 500 sin confundir a Java
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Autowired
    private com.asr.inventario_api.repository.RecepcionRepository recepcionRepository;
    @GetMapping
    public ResponseEntity<List<Recepcion>> listarRecepciones() {
        List<Recepcion> lista = recepcionRepository.findAll();
        return ResponseEntity.ok(lista);
    }
}