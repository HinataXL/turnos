package dao;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import model.Empleado;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;



public class EmpleadoDAO {
    private static final String FILE_PATH = "empleados.txt";

    public void guardarEmpleado(Empleado empleado) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            JSONObject json = new JSONObject();
            json.put("dpi", empleado.getDpi());
            json.put("nombreCompleto", empleado.getNombreCompleto());
            json.put("usuario", empleado.getUsuario());
            json.put("area", empleado.getArea());
            json.put("turno", empleado.getTurno());
            json.put("estado", empleado.getEstado());
            json.put("correo", empleado.getCorreo());
            json.put("contraseña", empleado.getContraseña());
            json.put("rol", empleado.getRol());

            writer.write(json.toString(4) + System.lineSeparator());
            System.out.println("Se creo Correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el empleado: " + e.getMessage());
        }
    }
    
    
    
            public List<Empleado> obtenerTodos() {
             List<Empleado> empleados = new ArrayList<>();
               try {
             BufferedReader reader = new BufferedReader(new FileReader("empleados.txt"));
             String linea;
             while ((linea = reader.readLine()) != null) {
            JSONObject json = new JSONObject(linea);
            Empleado emp = new Empleado();
            emp.setDpi(json.getString("dpi"));
            emp.setNombreCompleto(json.getString("nombreCompleto")); // corregido
            emp.setUsuario(json.getString("usuario"));
            emp.setArea(json.getString("area"));
            emp.setTurno(json.getString("turno"));
            emp.setEstado(json.getString("estado"));
            emp.setCorreo(json.getString("correo"));
            emp.setContraseña(json.getString("contraseña"));
            emp.setRol(json.getString("Rol"));// corregido
            empleados.add(emp);
        }
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return empleados;
}


    public Empleado buscarPorUsuario(String usuario) {
        List<Empleado> empleados = obtenerTodos(); // Método que lee todos los empleados del archivo
        for (Empleado emp : empleados) {
        if (emp.getUsuario().equalsIgnoreCase(usuario)) {
            return emp;
        }
    }
    return null; // Si no se encuentra
}


    public void asignarRol(String usuario, String rol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
