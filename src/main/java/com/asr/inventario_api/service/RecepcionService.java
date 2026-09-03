package com.asr.inventario_api.service;

import com.asr.inventario_api.model.Movimiento;
import com.asr.inventario_api.model.Producto;
import com.asr.inventario_api.model.Recepcion;
import com.asr.inventario_api.repository.MovimientoRepository;
import com.asr.inventario_api.repository.ProductoRepository;
import com.asr.inventario_api.repository.RecepcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RecepcionService {

    @Autowired
    private RecepcionRepository recepcionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Transactional
    public Recepcion registrarValidacion(Recepcion recepcion, Producto producto) {
        
        Producto productoActual;

        if (producto.getId() != null) {
            productoActual = productoRepository.findById(producto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + producto.getId()));
        } else {
            if (producto.getStockActual() == null) {
                producto.setStockActual(BigDecimal.ZERO);
            }
            productoActual = productoRepository.save(producto);
        }

        BigDecimal nuevoStock = productoActual.getStockActual().add(recepcion.getCantidad());
        productoActual.setStockActual(nuevoStock);
        productoRepository.save(productoActual);

        recepcion.setProductoId(productoActual.getId());
        if (recepcion.getEstado() == null) {
            recepcion.setEstado("CONFORME"); 
        }
        Recepcion recepcionGuardada = recepcionRepository.save(recepcion);

        Movimiento movimiento = new Movimiento();
        movimiento.setProductoId(productoActual.getId());
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(recepcion.getCantidad());
        movimiento.setMotivo("Validación de Ingreso. Pedido N°: " + recepcion.getNumeroPedido());
        movimiento.setResponsable(recepcion.getSolicitante());
        movimientoRepository.save(movimiento);

        return recepcionGuardada;
    }
}