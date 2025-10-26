package model;

/**
 * Esta clase es un "View Model" o DTO (Data Transfer Object).
 * Su propósito es combinar los datos de un objeto Solicitud y un objeto Empleado
 * para mostrarlos fácilmente en la tabla del administrador.
 */
public class SolicitudAdminView {

    private String usuario;
    private String nombreCompleto;
    private String area;
    private String correo;
    private String motivo;
    private String estado;
    private String telefono;

    // Constructor vacío
    public SolicitudAdminView() {
    }
    
    // Getters y Setters para todas las propiedades

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono;
    }
}

