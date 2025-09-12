package model;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * Clase para enviar correos electrónicos reales usando SMTP (ej. Gmail).
 */
public class EmailSender {

    // --- CONFIGURACIÓN ---
    // ¡IMPORTANTE! Reemplaza con tu correo y tu Contraseña de Aplicación.
    private static final String CORREO_REMITENTE = "rrhh.test.java@gmail.com"; 
    private static final String CONTRASENA_APLICACION = "agirvwyncmoukxjl"; // La contraseña de 16 dígitos sin espacios

     private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "465"; // Puerto para SSL

    /**
     * Envía un correo electrónico con contenido HTML.
     * @param destinatario La dirección de correo del destinatario.
     * @param asunto El asunto del correo.
     * @param mensajeHtml El cuerpo del correo en formato HTML.
     */
    public static void enviarCorreoHtml(String destinatario, String asunto, String mensajeHtml) {
        // 1. Configuración de las propiedades del servidor de correo.
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        // Propiedad para confiar en todos los certificados (ayuda con antivirus/firewalls).
        // ADVERTENCIA: Reduce la seguridad de la conexión. Usar solo si es necesario.
        props.put("mail.smtp.ssl.trust", "*");

        // Timeouts explícitos para evitar que la aplicación se congele.
        props.put("mail.smtp.connectiontimeout", "15000"); // 15 segundos
        props.put("mail.smtp.timeout", "15000");           // 15 segundos

        // 2. Creación de la sesión con autenticación.
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CORREO_REMITENTE, CONTRASENA_APLICACION);
            }
        });
        
        // Habilita la salida de depuración en la consola de NetBeans.
        session.setDebug(true);

        try {
            // 3. Creación y configuración del mensaje.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO_REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(mensajeHtml, "text/html; charset=utf-8");

            // 4. Envío del mensaje.
            Transport.send(message);

            // Notificación de éxito al usuario (opcional).
            JOptionPane.showMessageDialog(null, "Correo de notificación enviado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException e) {
            // Imprime el error completo en la consola y muestra un mensaje al usuario.
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo enviar el correo de notificación.\nError: " + e.getMessage(), "Error de Envío", JOptionPane.ERROR_MESSAGE);
        }
    }
}
