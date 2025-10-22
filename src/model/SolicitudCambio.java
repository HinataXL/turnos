package model;

public class SolicitudCambio {

    private String usuarioEmpleado; // Quien lo pide
    private String fechaInicial;
    private String fechaNueva;
    private String justificacion;
    private String estado; // Ej: "Pendiente", "Aprobado", "Rechazado"

    // --- Getters y Setters para todos los campos ---
    // (Puedes generarlos automáticamente en NetBeans con Alt+Insert)

    public String getUsuarioEmpleado() { return usuarioEmpleado; }
    public void setUsuarioEmpleado(String u) { this.usuarioEmpleado = u; }
    public String getFechaInicial() { return fechaInicial; }
    public void setFechaInicial(String f) { this.fechaInicial = f; }
    public String getFechaNueva() { return fechaNueva; }
    public void setFechaNueva(String f) { this.fechaNueva = f; }
    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String j) { this.justificacion = j; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
}
