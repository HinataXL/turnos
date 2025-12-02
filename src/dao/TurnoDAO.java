package dao;

import model.conexion;
import model.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    // --- GUARDAR TURNO ---
    public void guardarTurno(Turno turno) {
        String sql = "INSERT INTO turnos (usuario_empleado, fecha_inicio, fecha_fin, tipo_turno) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, turno.getUsuarioEmpleado());
            ps.setString(2, turno.getFechaInicio());
            ps.setString(3, turno.getFechaFin());
            ps.setString(4, turno.getTurno()); // Nombre del turno (Matutino, etc.)
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al asignar turno: " + e.getMessage());
        }
    }

    // --- OBTENER TURNOS POR USUARIO ---
    public List<Turno> obtenerTurnosPorUsuario(String usuario) {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turnos WHERE usuario_empleado = ?";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Turno t = new Turno();
                    t.setUsuarioEmpleado(rs.getString("usuario_empleado"));
                    t.setFechaInicio(rs.getString("fecha_inicio"));
                    t.setFechaFin(rs.getString("fecha_fin"));
                    t.setTurno(rs.getString("tipo_turno"));
                    
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener turnos: " + e.getMessage());
        }
        return lista;
    }
}