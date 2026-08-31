package com.asr.inventario_api.controller;

import com.asr.inventario_api.model.Movimiento;
import com.asr.inventario_api.model.Producto;
import com.asr.inventario_api.repository.MovimientoRepository;
import com.asr.inventario_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "http://localhost:5173/")
public class MovimientoController {

    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<Movimiento>> listarTodos() {
        return new ResponseEntity<>(movimientoRepository.findAllByOrderByFechaDesc(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarMovimiento(@RequestBody Movimiento movimiento) {
        Producto producto = productoRepository.findById(movimiento.getProductoId()).orElse(null);
        if (producto == null) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        if ("entrada".equalsIgnoreCase(movimiento.getTipo())) {
            producto.setStockActual(producto.getStockActual().add(movimiento.getCantidad()));
        } else {
            producto.setStockActual(producto.getStockActual().subtract(movimiento.getCantidad()));
        }
        
        productoRepository.save(producto);
        return new ResponseEntity<>(movimientoRepository.save(movimiento), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMovimiento(@PathVariable Long id) {
        Movimiento mov = movimientoRepository.findById(id).orElse(null);
        if (mov != null) {
            Producto producto = productoRepository.findById(mov.getProductoId()).orElse(null);
            if (producto != null) {
                if ("entrada".equalsIgnoreCase(mov.getTipo())) {
                    producto.setStockActual(producto.getStockActual().subtract(mov.getCantidad()));
                } else {
                    producto.setStockActual(producto.getStockActual().add(mov.getCantidad()));
                }
                productoRepository.save(producto);
            }
            movimientoRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoActualizado) {
        Movimiento movViejo = movimientoRepository.findById(id).orElse(null);
        if (movViejo == null) {
            return new ResponseEntity<>("Movimiento no encontrado", HttpStatus.NOT_FOUND);
        }

        Producto producto = productoRepository.findById(movViejo.getProductoId()).orElse(null);
        if (producto != null) {
            if ("entrada".equalsIgnoreCase(movViejo.getTipo())) {
                producto.setStockActual(producto.getStockActual().subtract(movViejo.getCantidad()));
            } else {
                producto.setStockActual(producto.getStockActual().add(movViejo.getCantidad()));
            }

            BigDecimal nuevaCantidad = movimientoActualizado.getCantidad();
            if ("entrada".equalsIgnoreCase(movViejo.getTipo())) {
                producto.setStockActual(producto.getStockActual().add(nuevaCantidad));
            } else {
                producto.setStockActual(producto.getStockActual().subtract(nuevaCantidad));
            }
            productoRepository.save(producto);
        }

        movViejo.setCantidad(movimientoActualizado.getCantidad());
        movViejo.setMotivo(movimientoActualizado.getMotivo());
        movViejo.setResponsable(movimientoActualizado.getResponsable());
        
        return new ResponseEntity<>(movimientoRepository.save(movViejo), HttpStatus.OK);
    }
}