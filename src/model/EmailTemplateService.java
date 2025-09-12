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
}

