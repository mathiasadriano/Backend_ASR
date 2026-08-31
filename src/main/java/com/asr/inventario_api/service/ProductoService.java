package com.asr.inventario_api.service;

import com.asr.inventario_api.model.Producto;
import com.asr.inventario_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setIngredienteActivo(productoActualizado.getIngredienteActivo());
            producto.setUnidad(productoActualizado.getUnidad());
            producto.setStockActual(productoActualizado.getStockActual());
            producto.setStockMinimo(productoActualizado.getStockMinimo());
            producto.setCostoUnitario(productoActualizado.getCostoUnitario());
            producto.setProveedor(productoActualizado.getProveedor());
            producto.setPresentacion(productoActualizado.getPresentacion());
            producto.setUbicacion(productoActualizado.getUbicacion());
            producto.setFechaVencimiento(productoActualizado.getFechaVencimiento());
            producto.setNotas(productoActualizado.getNotas());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}