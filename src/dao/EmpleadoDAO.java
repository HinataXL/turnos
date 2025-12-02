
   package dao;

import model.conexion;
import model.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // --- LISTAR TODOS ---
    public List<Empleado> obtenerTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                // Asegúrate de que los nombres de columna coincidan con tu BD
                emp.setNombre(rs.getString("nombre"));
                emp.setUsuario(rs.getString("usuario"));
                emp.setContraseña(rs.getString("password")); 
                emp.setCorreo(rs.getString("correo"));
                emp.setTelefono(rs.getString("telefono")); // Nuevo campo
                emp.setRol(rs.getString("rol"));
                // emp.setDpi(rs.getString("dpi")); // Si tienes DPI en tu BD
                
                lista.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // --- BUSCAR POR USUARIO (Para Login y Notificaciones) ---
    public Empleado buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM empleados WHERE usuario = ?";
        Empleado emp = null;

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = new Empleado();
                    emp.setNombre(rs.getString("nombre"));
                    emp.setUsuario(rs.getString("usuario"));
                    emp.setContraseña(rs.getString("password"));
                    emp.setCorreo(rs.getString("correo"));
                    emp.setTelefono(rs.getString("telefono"));
                    emp.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return emp;
    }

    // --- GUARDAR NUEVO EMPLEADO ---
    public boolean guardarEmpleado(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, usuario, password, correo, telefono, rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getUsuario());
            ps.setString(3, emp.getContraseña());
            ps.setString(4, emp.getCorreo());
            ps.setString(5, emp.getTelefono()); // Puede ser null si no tiene
            ps.setString(6, emp.getRol());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar empleado: " + e.getMessage());
            return false;
        }
    }
    
    // --- ACTUALIZAR ESTADO/ROL ---
    public void actualizarEstado(String usuario, String nuevoEstado) {
        // Nota: Si tu tabla empleados no tiene columna 'estado', 
        // tal vez necesites agregarla o ignorar este método.
        String sql = "UPDATE empleados SET estado = ? WHERE usuario = ?";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setString(2, usuario);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
        }
    }
}