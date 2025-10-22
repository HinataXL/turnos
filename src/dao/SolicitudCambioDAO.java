package dao;

import java.io.*;
import java.util.*;
import model.SolicitudCambio;
import org.json.JSONObject;

public class SolicitudCambioDAO {
    
    private static final String FILE_PATH = "solicitudes_cambio.txt";

    /**
     * Guarda una nueva solicitud de cambio en el archivo.
     */
    public void guardarSolicitud(SolicitudCambio solicitud) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            JSONObject json = new JSONObject();
            json.put("usuario", solicitud.getUsuarioEmpleado());
            json.put("fechaInicial", solicitud.getFechaInicial());
            json.put("fechaNueva", solicitud.getFechaNueva());
            json.put("justificacion", solicitud.getJustificacion());
            json.put("estado", solicitud.getEstado());
            
            writer.write(json.toString() + System.lineSeparator());
        }
    }

    /**
     * Lee todas las solicitudes del archivo (para la tabla del admin).
     */
    public List<SolicitudCambio> listarTodas() {
        List<SolicitudCambio> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                JSONObject json = new JSONObject(linea);
                SolicitudCambio s = new SolicitudCambio();
                s.setUsuarioEmpleado(json.getString("usuario"));
                s.setFechaInicial(json.getString("fechaInicial"));
                s.setFechaNueva(json.getString("fechaNueva"));
                s.setJustificacion(json.getString("justificacion"));
                s.setEstado(json.getString("estado"));
                lista.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
 
public void actualizarEstado(String usuario, String fechaInicial, String nuevoEstado) {
    // 1. Lee todas las solicitudes del archivo
    List<SolicitudCambio> solicitudes = listarTodas();
    
    // 2. Busca en la lista la solicitud que queremos cambiar
    for (SolicitudCambio s : solicitudes) {
        if (s.getUsuarioEmpleado().equals(usuario) && s.getFechaInicial().equals(fechaInicial)) {
            // ¡La encontramos! Actualizamos su estado.
            s.setEstado(nuevoEstado);
            break; // Salimos del bucle
        }
    }
    
    // 3. Guarda la lista completa de nuevo, sobrescribiendo el archivo
    guardarTodas(solicitudes);
}

/**
 * Método privado que sobrescribe el archivo completo con una lista de solicitudes.
 * @param solicitudes La lista completa de solicitudes a guardar.
 */
private void guardarTodas(List<SolicitudCambio> solicitudes) {
    // El 'false' en FileWriter es para SOBRESCRIBIR, no para añadir (append)
    try (FileWriter writer = new FileWriter(FILE_PATH, false)) {
        for (SolicitudCambio s : solicitudes) {
            JSONObject json = new JSONObject();
            json.put("usuario", s.getUsuarioEmpleado());
            json.put("fechaInicial", s.getFechaInicial());
            json.put("fechaNueva", s.getFechaNueva());
            json.put("justificacion", s.getJustificacion());
            json.put("estado", s.getEstado());
            writer.write(json.toString() + System.lineSeparator());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}