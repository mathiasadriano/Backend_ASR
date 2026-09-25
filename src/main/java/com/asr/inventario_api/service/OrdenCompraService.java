package com.asr.inventario_api.service;

import com.asr.inventario_api.model.OrdenCompra;
import com.asr.inventario_api.model.OrdenCompraDetalle;
import com.asr.inventario_api.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository repository;

    public List<OrdenCompra> obtenerTodas() {
        // Devuelve las OCs ordenadas por ID descendente (las más nuevas primero)
        return repository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }

    @Transactional
    public OrdenCompra guardarOrdenCompra(OrdenCompra orden) {
        // Aseguramos la relación bidireccional antes de guardar
        if (orden.getDetalles() != null) {
            for (OrdenCompraDetalle detalle : orden.getDetalles()) {
                detalle.setOrdenCompra(orden);
            }
        }
        return repository.save(orden);
    }
}