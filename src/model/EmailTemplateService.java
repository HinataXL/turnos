package model;

/**
 * Servicio para generar plantillas de correo electrónico en formato HTML.
 * Mantiene el código de diseño (HTML/CSS) separado de la lógica de la aplicación.
 */
public class EmailTemplateService {

    /**
     * Crea el contenido HTML para el correo de respuesta a una solicitud,
     * utilizando una plantilla de diseño profesional.
     * @param nombreEmpleado El nombre del empleado a quien se dirige el correo.
     * @param motivo El motivo de la solicitud (ej. "Vacaciones").
     * @param estado El resultado de la solicitud ("Aceptada" o "Rechazada").
     * @return Una cadena con el cuerpo del correo en formato HTML.
     */
    public String crearHtmlRespuesta(String nombreEmpleado, String motivo, String estado) {
        // --- LÓGICA DINÁMICA ---
        // Se define el mensaje principal según si la solicitud fue aceptada o rechazada.
        String mensajePrincipal;
        if ("Aceptada".equalsIgnoreCase(estado)) {
            mensajePrincipal = "Nos complace informarte que tu solicitud de ausencia por motivo de '<strong>" + motivo + "</strong>' ha sido <strong>ACEPTADA</strong>.";
        } else {
            mensajePrincipal = "Lamentamos informarte que tu solicitud de ausencia por motivo de '<strong>" + motivo + "</strong>' ha sido <strong>RECHAZADA</strong>.";
        }

        // Usamos StringBuilder para construir la cadena HTML de manera eficiente
        StringBuilder html = new StringBuilder();

        // --- INICIO DE LA PLANTILLA HTML ---
        html.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>")
            .append("<title>Notificación de Solicitud</title><style>")
            .append("body { background: #f4f6fa; font-family: 'Segoe UI', Arial, sans-serif; margin: 0; padding: 0; }")
            .append(".email-container { max-width: 540px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 4px 18px rgba(0,0,0,0.09); overflow: hidden; }")
            .append(".header { background: linear-gradient(90deg, #8AFFEB 0%, #B06DF7 100%); padding: 32px 28px 24px 28px; color: #1b1b1b; text-align: left; }")
            .append(".header-logo { font-size: 1.8em; font-weight: 700; letter-spacing: 1px; display: flex; align-items: center; }")
            .append(".animated-bell { width: 48px; height: 48px; margin-right: 14px; display: inline-block; }")
            // Animación de la campana (no todos los clientes de correo la soportan, pero no afecta la visualización si no lo hacen)
            .append("@keyframes bell-swing { 0% { transform: rotate(0deg);} 10% { transform: rotate(-18deg);} 20% { transform: rotate(14deg);} 30% { transform: rotate(-10deg);} 40% { transform: rotate(7deg);} 50% { transform: rotate(-4deg);} 60% { transform: rotate(2deg);} 70% { transform: rotate(-1deg);} 80% { transform: rotate(0deg);} 100% { transform: rotate(0deg);} }")
            .append(".bell-shape { transform-origin: 50% 15%; animation: bell-swing 1.6s cubic-bezier(.36,.07,.19,.97) infinite; }")
            .append(".header-subtitle { font-size: 1.1em; opacity: 0.92; margin-top: 6px; }")
            .append(".body-content { padding: 28px; color: #222; font-size: 1.08em; line-height: 1.7; }")
            .append(".signature { margin-top: 24px; color: #555; font-size: 0.98em; }")
            // Media Query para Responsividad
            .append("@media (max-width: 600px) { .header-logo { font-size: 1.3em; } .animated-bell { width: 32px; height: 32px; } }")
            .append("</style></head><body>")
            .append("<div class='email-container'>")
            // Header con tu diseño
            .append("<div class='header'>")
            .append("<div class='header-logo'>")
            .append("<span class='animated-bell'><svg class='bell-shape' viewBox='0 0 48 48' fill='none' aria-hidden='true'><ellipse cx='24' cy='41' rx='7' ry='3' fill='#fff' opacity='0.25'/><path d='M24 8c-6.6 0-12 5.4-12 12v7c0 2.2-1.8 4-4 4h32c-2.2 0-4-1.8-4-4v-7c0-6.6-5.4-12-12-12z' fill='#fff' stroke='#fff' stroke-width='2'/><path d='M18 39a6 6 0 0 0 12 0' fill='#fff' stroke='#fff' stroke-width='2'/><circle cx='24' cy='41' r='2' fill='#1976d2'/></svg></span>")
            .append("<div>Respuesta a tu Solicitud</div>")
            .append("</div>")
            .append("<div class='header-subtitle'>Seguimiento de tu permiso de ausencia</div>")
            .append("</div>")
            // Cuerpo del Mensaje
            .append("<div class='body-content'>")
            .append("Estimado/a <strong>").append(nombreEmpleado).append("</strong>,<br><br>")
            .append(mensajePrincipal).append("<br><br>")
            .append("Si tienes alguna pregunta, por favor, contacta con el departamento de Recursos Humanos.<br><br>")
            // Firma
            .append("<div class='signature'>")
            .append("Saludos cordiales,<br>")
            .append("<strong>Equipo de RRHH</strong>")
            .append("</div>")
            .append("</div>")
            .append("</div>")
            .append("</body></html>");
        
        return html.toString();
    }
    
    public String getAsuntoRespuestaCambio() {
    return "Respuesta a tu Solicitud de Cambio de Turno";
}

/**
 * Genera el cuerpo del correo en HTML para notificar el estado de un cambio de turno.
 * @param nombreEmpleado El nombre del empleado.
 * @param fechaInicial La fecha de la solicitud original.
 * @param estado "Aprobado" o "Rechazado".
 * @return Un String con el correo en formato HTML.
 */
public String getCuerpoRespuestaCambio(String nombreEmpleado, String fechaInicial, String estado) {
    
    // 1. Definir el mensaje principal y el color basado en el estado
    String mensajePrincipal;
    String colorEstado;
    String estadoVisible = estado.toUpperCase(); // "APROBADO" o "RECHAZADO"

    if ("Aprobado".equalsIgnoreCase(estado)) {
        colorEstado = "#28a745"; // Verde
        mensajePrincipal = "Te informamos que tu solicitud de cambio de turno para la fecha <strong>" + fechaInicial + "</strong> ha sido <strong style=\"color: " + colorEstado + ";\">" + estadoVisible + "</strong>.";
    } else {
        colorEstado = "#dc3545"; // Rojo
        mensajePrincipal = "Te informamos que tu solicitud de cambio de turno para la fecha <strong>" + fechaInicial + "</strong> ha sido <strong style=\"color: " + colorEstado + ";\">" + estadoVisible + "</strong>.";
    }

    // 2. Construir la plantilla HTML
    // (Este es tu HTML, adaptado a un StringBuilder de Java)
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
    html.append("<title>Notificación de Solicitud</title><style>");
    html.append("body { box-sizing: border-box; background: #f4f6fa; font-family: 'Segoe UI', Arial, sans-serif; margin: 0; padding: 0; }");
    html.append(".email-container { max-width: 540px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 4px 18px rgba(0,0,0,0.09); overflow: hidden; }");
    html.append(".header { background: linear-gradient(90deg, #8AFFEB 0%, #B06DF7 100%); padding: 32px 28px 24px 28px; color: #1b1b1b; text-align: left; }");
    html.append(".header-logo { font-size: 1.8em; font-weight: 700; letter-spacing: 1px; display: flex; align-items: center; }");
    html.append(".animated-bell { width: 48px; height: 48px; margin-right: 14px; display: inline-block; }");
    html.append("@keyframes bell-swing { 0% { transform: rotate(0deg); } 10% { transform: rotate(-18deg); } 20% { transform: rotate(14deg); } 30% { transform: rotate(-10deg); } 40% { transform: rotate(7deg); } 50% { transform: rotate(-4deg); } 60% { transform: rotate(2deg); } 70% { transform: rotate(-1rdeg); } 80% { transform: rotate(0deg); } 100% { transform: rotate(0deg); } }");
    html.append(".bell-shape { transform-origin: 50% 15%; animation: bell-swing 1.6s cubic-bezier(.36,.07,.19,.97) infinite; }");
    html.append(".header-subtitle { font-size: 1.1em; opacity: 0.92; margin-top: 6px; }"); // Hecho más genérico
    html.append(".body-content { padding: 28px; color: #222; font-size: 1.08em; line-height: 1.7; }");
    html.append(".signature { margin-top: 24px; color: #555; font-size: 0.98em; }");
    html.append("@media (max-width: 600px) { .header-logo { font-size: 1.3em; } .animated-bell { width: 32px; height: 32px; } }");
    html.append("</style></head><body>");
    html.append("<div class='email-container'>");
    html.append("  <div class='header'>");
    html.append("    <div class='header-logo'>");
    html.append("      <span class='animated-bell'>");
    html.append("        <svg class='bell-shape' viewBox='0 0 48 48' fill='none' aria-hidden='true'><ellipse cx='24' cy='41' rx='7' ry='3' fill='#1b1b1b' opacity='0.25'/><path d='M24 8c-6.6 0-12 5.4-12 12v7c0 2.2-1.8 4-4 4h32c-2.2 0-4-1.8-4-4v-7c0-6.6-5.4-12-12-12z' fill='#1b1b1b' stroke='#1b1b1b' stroke-width='2'/><path d='M18 39a6 6 0 0 0 12 0' fill='#1b1b1b' stroke='#1b1b1b' stroke-width='2'/><circle cx='24' cy='41' r='2' fill='#fff'/></svg>");
    html.append("      </span>");
    html.append("      <div>SOLICITUD DE TURNO</div>");
    html.append("    </div>");
    // ---- MODIFICACIÓN IMPORTANTE ----
    // Cambié el subtítulo para que coincida con este flujo
    html.append("    <div class='header-subtitle'>Respuesta a tu Cambio de Turno</div>");
    html.append("  </div>");
    html.append("  <div class='body-content'>");
    // ---- INSERCIÓN DE DATOS ----
    html.append("    Estimado/a <strong>").append(nombreEmpleado).append("</strong>,<br><br>");
    html.append("    ").append(mensajePrincipal).append("<br><br>");
    html.append("    Si tienes alguna pregunta, por favor, contacta con el departamento de Recursos Humanos.<br><br>");
    html.append("    <div class='signature'>");
    html.append("      Saludos cordiales,<br>");
    html.append("      <strong>Equipo de RRHH</strong>");
    html.append("    </div>");
    html.append("  </div>");
    html.append("</div>");
    // No incluimos la etiqueta <script> de tu ejemplo,
    // ya que no se ejecutará en la mayoría de los clientes de correo.
    html.append("</body></html>");

    return html.toString();
}

public String getAsuntoAsignacionTurno() {
    return "Notificación: Se te ha asignado un nuevo turno";
}

/**
 * Genera el cuerpo del correo en HTML para una nueva asignación de turno.
 * @param nombreEmpleado El nombre del empleado.
 * @param fechaInicio La fecha de inicio del turno.
 * @param fechaFin La fecha de fin del turno.
 * @param nombreTurno El nombre del turno (ej: "Turno Matutino").
 * @return Un String con el correo en formato HTML.
 */
public String getCuerpoAsignacionTurno(String nombreEmpleado, String fechaInicio, String fechaFin, String nombreTurno) {
    
    // 1. Definir el mensaje principal
    String mensajePrincipal = "Se te ha asignado un nuevo turno con los siguientes detalles:<br><br>" +
                              "   - <strong>Turno:</strong> " + nombreTurno + "<br>" +
                              "   - <strong>Desde:</strong> " + fechaInicio + "<br>" +
                              "   - <strong>Hasta:</strong> " + fechaFin + "<br><br>" +
                              "Por favor, revisa tu calendario.";

    // 2. Construir la plantilla HTML (reutilizando la estructura que me diste)
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
    html.append("<title>Notificación de Turno</title><style>");
    // (Pegas aquí todo el <style>...</style> de tu otra plantilla)
    html.append("body { box-sizing: border-box; background: #f4f6fa; font-family: 'Segoe UI', Arial, sans-serif; margin: 0; padding: 0; }");
    html.append(".email-container { max-width: 540px; margin: 40px auto; background: #fff; border-radius: 10px; box-shadow: 0 4px 18px rgba(0,0,0,0.09); overflow: hidden; }");
    html.append(".header { background: linear-gradient(90deg, #8AFFEB 0%, #B06DF7 100%); padding: 32px 28px 24px 28px; color: #1b1b1b; text-align: left; }");
    html.append(".header-logo { font-size: 1.8em; font-weight: 700; letter-spacing: 1px; display: flex; align-items: center; }");
    // ... (etc, todo el CSS) ...
    html.append("</style></head><body>");
    html.append("<div class='email-container'>");
    html.append("  <div class='header'>");
    html.append("    <div class='header-logo'>");
    // ... (tu SVG de la campana) ...
    html.append("      </span>");
    html.append("      <div>ASIGNACIÓN DE TURNO</div>"); // Título cambiado
    html.append("    </div>");
    html.append("    <div class='header-subtitle'>Notificación del sistema</div>"); // Subtítulo cambiado
    html.append("  </div>");
    html.append("  <div class='body-content'>");
    html.append("    Estimado/a <strong>").append(nombreEmpleado).append("</strong>,<br><br>");
    html.append("    ").append(mensajePrincipal).append("<br><br>"); // Mensaje insertado
    html.append("    <div class='signature'>");
    html.append("      Saludos cordiales,<br>");
    html.append("      <strong>Equipo de RRHH</strong>");
    html.append("    </div>");
    html.append("  </div>");
    html.append("</div></body></html>");

    return html.toString();
}

}

