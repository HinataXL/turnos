package dao;

import model.conexion;
import model.SolicitudCambio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudCambioDAO {

    // --- GUARDAR SOLICITUD ---
    public void guardarSolicitud(SolicitudCambio solicitud) {
        // Necesitamos obtener el ID del empleado basado en su usuario primero
        // O guardar directamente el usuario si tu tabla usa VARCHAR para empleado
        String sql = "INSERT INTO solicitudes (usuario_empleado, tipo, fecha_inicio, fecha_fin, motivo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, solicitud.getUsuarioEmpleado());
            ps.setString(2, "Cambio de Turno"); // O solicitud.getTipo()
            ps.setString(3, solicitud.getFechaInicial());
            ps.setString(4, solicitud.getFechaNueva()); // Asumiendo que guardas esto en fecha_fin
            ps.setString(5, solicitud.getJustificacion());
            ps.setString(6, solicitud.getEstado()); // "Pendiente"
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- LISTAR TODAS ---
    public List<SolicitudCambio> listarTodas() {
        List<SolicitudCambio> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes"; // Puedes filtrar WHERE estado='Pendiente'

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitudCambio s = new SolicitudCambio();
                s.setUsuarioEmpleado(rs.getString("usuario_empleado"));
                s.setFechaInicial(rs.getString("fecha_inicio"));
                s.setFechaNueva(rs.getString("fecha_fin"));
                s.setJustificacion(rs.getString("motivo"));
                s.setEstado(rs.getString("estado"));
                
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar solicitudes: " + e.getMessage());
        }
        return lista;
    }

    // --- ACTUALIZAR ESTADO (Aprobar/Rechazar) ---
    public void actualizarEstado(String usuario, String fechaInicial, String nuevoEstado) {
        // Usamos usuario y fecha como identificadores (sería mejor usar ID único de solicitud)
        String sql = "UPDATE solicitudes SET estado = ? WHERE usuario_empleado = ? AND fecha_inicio = ?";
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setString(2, usuario);
            ps.setString(3, fechaInicial);
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar solicitud: " + e.getMessage());
        }
    }
}