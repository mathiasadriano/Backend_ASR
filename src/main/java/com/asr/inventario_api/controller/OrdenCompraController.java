package com.asr.inventario_api.controller;

import com.asr.inventario_api.model.OrdenCompra;
import com.asr.inventario_api.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
@CrossOrigin(origins = "*") 
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService service;

    @GetMapping
    public List<OrdenCompra> listarTodas() {
        return service.obtenerTodas();
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@RequestBody OrdenCompra orden) {
        try {
            OrdenCompra nuevaOrden = service.guardarOrdenCompra(orden);
            return ResponseEntity.ok(nuevaOrden);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}