package model;

public class Turno {
    private String usuarioEmpleado;
    private String fechaInicio;
    private String fechaFin;
    private String turno;

    // Getters y Setters para todos los campos...
    public String getUsuarioEmpleado() { return usuarioEmpleado; }
    public void setUsuarioEmpleado(String usuarioEmpleado) { this.usuarioEmpleado = usuarioEmpleado; }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}