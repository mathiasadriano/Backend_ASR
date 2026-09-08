package com.asr.inventario_api.controller;

import com.asr.inventario_api.service.ReporteDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteDiarioService reporteDiarioService;

    @PostMapping("/enviar-diario")
    public ResponseEntity<String> enviarReporte(@RequestParam String secret) {
        // Clave secreta para que nadie más pueda disparar el correo
        if (!"ASR_PAMPA_2026_SECRETO".equals(secret)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        String resultado = reporteDiarioService.generarYEnviarReporteDiario();
        return ResponseEntity.ok(resultado);
    }
}