package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject; // Importante: Asegúrate de tener la librería org.json

/**
 * Servicio Singleton para gestionar la bitácora de la aplicación en formato JSON.
 * Registra eventos como inicio/cierre de sesión y acciones de los usuarios.
 */
public class BitacoraService {

    private static BitacoraService instance;
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "bitacora.txt";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private String usuarioActual;

    // Constructor privado para el patrón Singleton
    private BitacoraService() {}

    /**
     * Obtiene la única instancia del servicio.
     * @return La instancia de BitacoraService.
     */
    public static synchronized BitacoraService getInstance() {
        if (instance == null) {
            instance = new BitacoraService();
        }
        return instance;
    }

    /**
     * Registra el inicio de sesión de un usuario.
     * @param usuario El nombre del usuario que ha iniciado sesión.
     */
    public void logInicioSesion(String usuario) {
        this.usuarioActual = usuario;
        escribirLog("INICIO SESIÓN", "El usuario ha iniciado sesión.");
    }

    /**
     * Registra una acción realizada por el usuario logueado.
     * @param descripcion La descripción de la acción realizada.
     */
    public void logAccion(String descripcion) {
        if (this.usuarioActual == null) {
            System.err.println("Intento de registrar acción sin un usuario logueado.");
            return;
        }
        escribirLog("ACCIÓN", descripcion);
    }

    /**
     * Registra el cierre de sesión del usuario actual.
     */
    public void logCierreSesion() {
        if (this.usuarioActual != null) {
            escribirLog("CIERRE SESIÓN", "El usuario ha cerrado la sesión.");
            this.usuarioActual = null; // Limpia el usuario actual
        }
    }
    
    /**
     * Método privado y sincronizado para escribir en el archivo de bitácora en formato JSON.
     * @param tipo El tipo de evento (INICIO SESIÓN, ACCIÓN, etc.).
     * @param descripcion La descripción detallada del evento.
     */
    private synchronized void escribirLog(String tipo, String descripcion) {
        // --- AQUÍ ESTÁ EL CAMBIO ---
        // 1. Crea un objeto JSON para la entrada de la bitácora.
        JSONObject logEntry = new JSONObject();
        
        // 2. Añade los datos al objeto JSON.
        logEntry.put("timestamp", LocalDateTime.now().format(formatter));
        logEntry.put("usuario", (this.usuarioActual != null) ? this.usuarioActual : "SISTEMA");
        logEntry.put("tipo_evento", tipo);
        logEntry.put("descripcion", descripcion);

        // 3. Escribe el objeto JSON como una cadena en el archivo.
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            pw.println(logEntry.toString());

        } catch (IOException e) {
            System.err.println("Error al escribir en la bitácora: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

