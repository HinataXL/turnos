package model;

public class Solicitud {
    private String tipoSolicitud;
    private String adminAprobo;
    private String usuario;
    private String fecha;
    private String justificacion;

    public Solicitud() {}

    public Solicitud(String tipoSolicitud, String adminAprobo, String usuario, String fecha, String justificacion) {
        this.tipoSolicitud = tipoSolicitud;
        this.adminAprobo = adminAprobo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.justificacion = justificacion;
    }

    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }

    public String getAdminAprobo() { return adminAprobo; }
    public void setAdminAprobo(String adminAprobo) { this.adminAprobo = adminAprobo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
}
