package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa una solicitud de inactivación o permiso hecha por un empleado.
 * Esta clase es un Pojo (Plain Old Java Object) que sirve como modelo de datos.
 */
public class Solicitud {

    private String usuario;
    private String tipo; // "Vacaciones", "Licencia de Cumpleaños", etc.
    private String justificacion;
    private LocalDate fecha;
    private String estado; // "Pendiente", "Aprobada", "Rechazada"

    public Solicitud() {
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // Sobrescribimos equals y hashCode para comparar objetos Solicitud
    // de manera más fiable, especialmente al buscar en listas.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solicitud solicitud = (Solicitud) o;
        return Objects.equals(usuario, solicitud.usuario) &&
               Objects.equals(fecha, solicitud.fecha) &&
               Objects.equals(tipo, solicitud.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, fecha, tipo);
    }
}

