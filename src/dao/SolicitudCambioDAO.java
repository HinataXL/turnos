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
    
    // Aquí podrías añadir métodos futuros como "actualizarEstado"
}