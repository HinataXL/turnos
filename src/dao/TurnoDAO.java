package dao;

import java.io.*;
import java.util.*;
import model.Turno;
import org.json.JSONObject;

public class TurnoDAO {
    private static final String FILE_PATH = "turnos.txt";

    // Método para guardar un nuevo turno
    public void guardarTurno(Turno turno) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            JSONObject json = new JSONObject();
            json.put("usuarioEmpleado", turno.getUsuarioEmpleado());
            json.put("fechaInicio", turno.getFechaInicio());
            json.put("fechaFin", turno.getFechaFin());
            json.put("turno", turno.getTurno());
            writer.write(json.toString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener los turnos de UN solo usuario
    public List<Turno> obtenerTurnosPorUsuario(String usuario) {
        List<Turno> turnosDelUsuario = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                JSONObject json = new JSONObject(linea);
                if (usuario.equalsIgnoreCase(json.getString("usuarioEmpleado"))) {
                    Turno t = new Turno();
                    t.setUsuarioEmpleado(json.getString("usuarioEmpleado"));
                    t.setFechaInicio(json.getString("fechaInicio"));
                    t.setFechaFin(json.getString("fechaFin"));
                    t.setTurno(json.getString("turno"));
                    turnosDelUsuario.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return turnosDelUsuario;
    }
}