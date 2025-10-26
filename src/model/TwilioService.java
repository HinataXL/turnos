package model; 

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {

    
    public static final String ACCOUNT_SID = "AC6ab55ba2a5fe03c3a926fb9a2ad5e75f"; 
    public static final String AUTH_TOKEN = "9e5a1aaa8321fafa8cdba23fe610504b";  
    public static final String TWILIO_PHONE_NUMBER = "whatsapp:+14155238886"; 
    // ---------------------------------------------

    public TwilioService() {
        // Inicializa la conexión con Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Envía un mensaje de WhatsApp.
     * @param destinatario El número del empleado en formato E.164 (ej: +50212345678)
     * @param mensaje El cuerpo del mensaje a enviar.
     */
    public boolean enviarWhatsApp(String destinatario, String mensaje) {
        try {
            // Asegúrate de que el formato sea "whatsapp:+502..."
            String numeroFormateado = "whatsapp:" + destinatario;

            System.out.println("Enviando WhatsApp a: " + numeroFormateado);

            Message.creator(
                new PhoneNumber(numeroFormateado),       // A dónde va
                new PhoneNumber(TWILIO_PHONE_NUMBER),  // Desde dónde se envía (el Sandbox)
                mensaje)                               // El mensaje
            .create();

            System.out.println("WhatsApp enviado exitosamente.");
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al enviar WhatsApp: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}