
package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import model.Empleado;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;

public class EmpleadoDAO {
    private static final String FILE_PATH = "empleados.txt";

    public void guardarEmpleado(Empleado empleado) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            JSONObject json = new JSONObject();
            json.put("dpi", empleado.getDPI());
            json.put("nombre", empleado.getNombre());
            json.put("usuario", empleado.getUsuario());
            json.put("area", empleado.getArea());
            json.put("turno", empleado.getTurno());
            json.put("estado", empleado.getEstado());
            json.put("correo", empleado.getCorreo());
            json.put("contraseña", empleado.getContraseña());
            json.put("rol", empleado.getRol());

            writer.write(json.toString() + System.lineSeparator());
            System.out.println("Se creó correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el empleado: " + e.getMessage());
        }
    }

    public List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                JSONObject json = new JSONObject(linea);
                Empleado emp = new Empleado();
                emp.setDPI(json.getString("dpi"));
                emp.setNombre(json.getString("nombre"));
                emp.setUsuario(json.getString("usuario"));
                emp.setArea(json.getString("area"));
                emp.setTurno(json.getString("turno"));
                emp.setEstado(json.getString("estado"));
                emp.setCorreo(json.getString("correo"));
                emp.setContraseña(json.getString("contraseña"));
                emp.setRol(json.getString("rol"));
                empleados.add(emp);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error al leer empleados: " + e.getMessage());
        }
        return empleados;
    }

    public Empleado buscarPorUsuario(String usuario) {
        System.out.println("Buscando usuario: " + usuario);
        List<Empleado> empleados = obtenerTodos();
        System.out.println("Total empleados leídos: " + empleados.size());

        for (Empleado emp : empleados) {
            System.out.println("Comparando con: " + emp.getUsuario());
            if (emp.getUsuario().equalsIgnoreCase(usuario)) {
                System.out.println("¡Empleado encontrado!");
                return emp;
            }
        }

        System.out.println("Empleado no encontrado.");
        return null;
    }

    public String obtenerCorreoPorUsuario(String usuario) {
        List<Empleado> empleados = obtenerTodos();
        for (Empleado emp : empleados) {
            if (emp.getUsuario().equalsIgnoreCase(usuario)) {
                return emp.getCorreo();
            }
        }
        return null;
    }

    public void actualizarRol(String usuario, String nuevoRol) {
        List<Empleado> empleados = obtenerTodos();
        for (Empleado emp : empleados) {
            if (emp.getUsuario().equalsIgnoreCase(usuario)) {
                emp.setRol(nuevoRol);
                break;
            }
        }
        guardarTodos(empleados);
    }

    public void guardarTodos(List<Empleado> empleados) {
        try (FileWriter writer = new FileWriter(FILE_PATH, false)) { // Sobrescribe el archivo
            for (Empleado emp : empleados) {
                JSONObject json = new JSONObject();
                json.put("dpi", emp.getDPI());
                json.put("nombre", emp.getNombre());
                json.put("usuario", emp.getUsuario());
                json.put("area", emp.getArea());
                json.put("turno", emp.getTurno());
                json.put("estado", emp.getEstado());
                json.put("correo", emp.getCorreo());
                json.put("contraseña", emp.getContraseña());
                json.put("rol", emp.getRol());

                writer.write(json.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar todos los empleados: " + e.getMessage());
        }
    }
}





    
    