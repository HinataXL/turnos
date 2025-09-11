package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Solicitud;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Data Access Object (DAO) para manejar las operaciones de lectura y escritura
 * de objetos Solicitud en un archivo de texto en formato JSON.
 */
public class SolicitudDAO {
    
    // La ruta ahora es una constante estática, definida una sola vez para la clase.
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "solicitudes.txt";

    public SolicitudDAO() {
        // El constructor ahora está vacío ya que la ruta se inicializa arriba.
    }

    /**
     * Guarda una nueva solicitud añadiéndola al final del archivo.
     * @param solicitud El objeto Solicitud a guardar.
     * @throws IOException Si ocurre un error durante la escritura del archivo.
     */
    public void guardarSolicitud(Solicitud solicitud) throws IOException {
        JSONObject json = new JSONObject();
        json.put("usuario", solicitud.getUsuario());
        json.put("tipo", solicitud.getTipo());
        json.put("justificacion", solicitud.getJustificacion());
        json.put("fecha", solicitud.getFecha().toString()); // Guarda la fecha como texto (YYYY-MM-DD)
        json.put("estado", solicitud.getEstado());
        
        // try-with-resources asegura que los recursos se cierren automáticamente.
        try (FileWriter fw = new FileWriter(FILE_PATH, true); // true para añadir al final (append)
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(json.toString());
        }
    }

    /**
     * Lee todas las solicitudes del archivo.
     * @return Una lista de todos los objetos Solicitud.
     */
    public List<Solicitud> obtenerTodasLasSolicitudes() {
        List<Solicitud> todas = new ArrayList<>();
        File archivo = new File(FILE_PATH);
        if (!archivo.exists()) {
            return todas; // Devuelve una lista vacía si el archivo no existe.
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; // Ignora líneas vacías
                
                JSONObject json = new JSONObject(linea);
                Solicitud sol = new Solicitud();
                sol.setUsuario(json.getString("usuario"));
                sol.setTipo(json.getString("tipo"));
                sol.setJustificacion(json.getString("justificacion"));
                sol.setFecha(LocalDate.parse(json.getString("fecha"))); // Convierte el texto de nuevo a LocalDate
                sol.setEstado(json.getString("estado"));
                todas.add(sol);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error al leer el archivo de solicitudes: " + e.getMessage());
        }
        return todas;
    }

    /**
     * Actualiza el estado de una solicitud específica basándose en el usuario, tipo y fecha.
     * @param usuario El usuario de la solicitud a actualizar.
     * @param tipo El tipo de la solicitud a actualizar.
     * @param fecha La fecha de la solicitud a actualizar.
     * @param nuevoEstado El nuevo estado a asignar ("Aprobada" o "Rechazada").
     */
    public void actualizarEstadoSolicitud(String usuario, String tipo, LocalDate fecha, String nuevoEstado) {
        List<Solicitud> todas = obtenerTodasLasSolicitudes();
        boolean encontrado = false;
        for (Solicitud sol : todas) {
            if (sol.getUsuario().equalsIgnoreCase(usuario) && 
                sol.getTipo().equals(tipo) && 
                sol.getFecha().equals(fecha)) {
                
                sol.setEstado(nuevoEstado);
                encontrado = true;
                break;
            }
        }
        
        if (encontrado) {
            // Si se encontró y modificó la solicitud, sobrescribe el archivo con la lista actualizada.
            guardarTodas(todas);
        }
    }
    
    /**
     * Método privado que sobrescribe el archivo de solicitudes con una lista completa.
     * @param solicitudes La lista de solicitudes a guardar.
     */
    private void guardarTodas(List<Solicitud> solicitudes) {
        try (FileWriter fw = new FileWriter(FILE_PATH, false); // false para sobrescribir el archivo
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            for (Solicitud sol : solicitudes) {
                JSONObject json = new JSONObject();
                json.put("usuario", sol.getUsuario());
                json.put("tipo", sol.getTipo());
                json.put("justificacion", sol.getJustificacion());
                json.put("fecha", sol.getFecha().toString());
                json.put("estado", sol.getEstado());
                pw.println(json.toString());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar todas las solicitudes: " + e.getMessage());
        }
    }
}

