package com.asr.inventario_api.service;

import com.asr.inventario_api.model.ReporteIngreso;
import com.asr.inventario_api.repository.ReporteIngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class ReporteDiarioService {

    @Autowired
    private ReporteIngresoRepository reporteIngresoRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String generarYEnviarReporteDiario() {
        // 1. Obtener la fecha de hoy en Perú
        ZoneId zonaLima = ZoneId.of("America/Lima");
        LocalDate hoy = LocalDate.now(zonaLima);

        // 2. Buscar en la BD usando la nueva función del repositorio
        List<ReporteIngreso> ingresos = reporteIngresoRepository.findByFecha(hoy);

        if (ingresos.isEmpty()) {
            return "No hubo Registros de Ingreso hoy (" + hoy + "). No se envió correo.";
        }

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            String[] destinatarios = {"jlopez@asr.com.pe", "mathiasadrianohl@gmail.com"};
            helper.setTo(destinatarios);
            helper.setSubject("Reporte de Registro de Ingresos - " + hoy);

            StringBuilder html = new StringBuilder();
            html.append("<h2 style='color: #2b6c56;'>Reporte Diario: Documentos y Guías de Ingreso</h2>");
            html.append("<table border='1' cellpadding='8' style='border-collapse: collapse; width: 100%; font-family: Arial, sans-serif;'>");
            html.append("<tr style='background-color: #538b76; color: white;'>");
            
            // Cabeceras de la tabla
            html.append("<th>Insumo</th><th>Cantidad</th><th>N° Pedido</th><th>Guía/Factura</th><th>Solicitante</th></tr>");

            // Llenado de datos con los nombres exactos de tu modelo
            for (ReporteIngreso ingreso : ingresos) {
                html.append("<tr style='text-align: center;'>");
                html.append("<td>").append(ingreso.getInsumo()).append("</td>");
                html.append("<td>").append(ingreso.getCantidad()).append("</td>");
                html.append("<td>").append(ingreso.getNumeroPedido()).append("</td>");
                html.append("<td>").append(ingreso.getGuiaFactura()).append("</td>");
                html.append("<td>").append(ingreso.getSolicitante()).append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
            html.append("<br><p style='color: #555;'><em>Sistema Automático de Gestión - ASR Agrícola</em></p>");

            helper.setText(html.toString(), true);
            mailSender.send(mensaje);

            return "Correo enviado con éxito. Total registros: " + ingresos.size();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}