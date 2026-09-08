package com.asr.inventario_api.service;

import com.asr.inventario_api.model.Movimiento;
import com.asr.inventario_api.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.time.*;
import java.util.List;

@Service
public class ReporteDiarioService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String generarYEnviarReporteDiario() {
        ZoneId zonaLima = ZoneId.of("America/Lima");
        LocalDate hoy = LocalDate.now(zonaLima);
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(LocalTime.MAX);

        List<Movimiento> movimientos = movimientoRepository.findByFechaBetween(inicioDia, finDia);

        if (movimientos.isEmpty()) {
            return "No hubo movimientos hoy (" + hoy + "). No se envió correo.";
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            String[] destinatarios = {"jlopez@asr.com.pe", "mathiasadrianohl@gmail.com"};
            helper.setTo(destinatarios);
            helper.setSubject("Reporte Diario de Almacén Pampa Grande - " + hoy);

            StringBuilder html = new StringBuilder();
            html.append("<h2 style='color: #2E7D32;'>Resumen de Movimientos del Día</h2>");
            html.append("<table border='1' cellpadding='8' style='border-collapse: collapse; width: 100%; font-family: Arial, sans-serif;'>");
            html.append("<tr style='background-color: #4CAF50; color: white;'>");
            html.append("<th>Tipo</th><th>Producto</th><th>Cantidad</th><th>Motivo</th><th>Responsable</th></tr>");

            for (Movimiento m : movimientos) {
                String colorFondo = m.getTipo().equalsIgnoreCase("entrada") ? "#E8F5E9" : "#FFEBEE";
                String tipoMayus = m.getTipo().toUpperCase();
                String nombreProd = m.getProducto() != null ? m.getProducto().getNombre() : "Producto Desconocido";
                
                html.append("<tr style='background-color: ").append(colorFondo).append("; text-align: center;'>");
                html.append("<td><strong>").append(tipoMayus).append("</strong></td>");
                html.append("<td>").append(nombreProd).append("</td>");
                html.append("<td>").append(m.getCantidad()).append("</td>");
                html.append("<td>").append(m.getMotivo()).append("</td>");
                html.append("<td>").append(m.getResponsable()).append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
            html.append("<br><p style='color: #555;'><em>Sistema Automático de Inventario - ASR Agrícola</em></p>");

            helper.setText(html.toString(), true);
            mailSender.send(mensaje);

            return "Correo enviado con éxito. Total movimientos: " + movimientos.size();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}