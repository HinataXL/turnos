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
 * Data Access Object para gestionar las operaciones de lectura y escritura
 * de objetos Solicitud en un archivo de texto en formato JSON.
 */
public class SolicitudDAO {

    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "solicitudes.txt";

    /**
     * Guarda una nueva solicitud en el archivo, añadiéndola al final.
     * @param solicitud El objeto Solicitud a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarSolicitud(Solicitud solicitud) throws IOException {
        JSONObject json = new JSONObject();
        json.put("usuario", solicitud.getUsuario());
        json.put("tipo", solicitud.getTipo());
        json.put("justificacion", solicitud.getJustificacion());
        json.put("fecha", solicitud.getFecha().toString());
        json.put("estado", solicitud.getEstado());

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(json.toString());
        }
    }

    /**
     * Lee todas las líneas del archivo y las convierte en una lista de objetos Solicitud.
     * @return Una lista de todas las solicitudes.
     * @throws IOException Si ocurre un error de lectura.
     */
    public List<Solicitud> obtenerTodas() throws IOException {
        List<Solicitud> solicitudes = new ArrayList<>();
        File archivo = new File(FILE_PATH);
        if (!archivo.exists()) {
            return solicitudes; // Devuelve lista vacía si el archivo no existe
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                try {
                    JSONObject json = new JSONObject(linea);
                    Solicitud sol = new Solicitud();
                    sol.setUsuario(json.optString("usuario", ""));
                    sol.setTipo(json.optString("tipo", ""));
                    sol.setJustificacion(json.optString("justificacion", ""));
                    sol.setFecha(LocalDate.parse(json.optString("fecha")));
                    sol.setEstado(json.optString("estado", ""));
                    solicitudes.add(sol);
                } catch (JSONException e) {
                    System.err.println("Error al parsear línea JSON: " + linea + " - " + e.getMessage());
                }
            }
        }
        return solicitudes;
    }

    // --- MÉTODO NUEVO ---
    /**
     * Filtra las solicitudes y devuelve solo aquellas que coinciden con el estado especificado.
     * @param estado El estado por el cual filtrar (ej. "Pendiente").
     * @return Una lista de solicitudes que coinciden con el estado.
     * @throws IOException Si ocurre un error de lectura.
     */
    public List<Solicitud> obtenerSolicitudesPorEstado(String estado) throws IOException {
        List<Solicitud> todas = obtenerTodas();
        List<Solicitud> filtradas = new ArrayList<>();
        for (Solicitud sol : todas) {
            if (sol.getEstado().equalsIgnoreCase(estado)) {
                filtradas.add(sol);
            }
        }
        return filtradas;
    }

    /**
     * Actualiza el estado de una solicitud específica.
     * @param usuario El usuario de la solicitud a actualizar.
     * @param motivo El motivo/justificación de la solicitud a actualizar.
     * @param nuevoEstado El nuevo estado a asignar ("Aceptada" o "Rechazada").
     * @throws IOException Si ocurre un error de lectura/escritura.
     */
    public void actualizarEstadoSolicitud(String usuario, String motivo, String nuevoEstado) throws IOException {
        List<Solicitud> solicitudes = obtenerTodas();
        boolean actualizada = false;
        for (Solicitud sol : solicitudes) {
            if (sol.getUsuario().equalsIgnoreCase(usuario) && sol.getJustificacion().equalsIgnoreCase(motivo) && sol.getEstado().equalsIgnoreCase("Pendiente")) {
                sol.setEstado(nuevoEstado);
                actualizada = true;
                break;
            }
        }
        if (actualizada) {
            guardarTodas(solicitudes);
        }
    }

    /**
     * Reescribe el archivo completo con la lista de solicitudes proporcionada.
     * @param solicitudes La lista completa de solicitudes a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    private void guardarTodas(List<Solicitud> solicitudes) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_PATH, false); // false para sobrescribir
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
        }
    }
}

