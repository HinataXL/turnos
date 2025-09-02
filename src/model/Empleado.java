/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author erick
 */
public class Empleado {
    
    private String dpi;
    private String nombre;
    private String usuario;
    private String area;
    private String turno;
    private String estado;
    private String correo;
    private String contraseña;
    //private String rol;

    public Empleado() {
        
    }

    

    // Constructor
    
    //public String getRol(){
    //return rol;
    //}
    
    public Empleado(String dpi, String nombreCompleto, String usuario, String area,
                    String turno, String estado, String correo, String contraseña) {
        this.dpi = dpi;
        this.nombre = nombreCompleto;
        this.usuario = usuario;
        this.area = area;
        this.turno = turno;
        this.estado = estado;
        this.correo = correo;
        this.contraseña = contraseña;
        //this.rol = rol;
    }

    // Getters y Setters
    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    //public void setRol(String rol) {
   // this.rol = rol;
    //}

    @Override
    public String toString() {
        return "Empleado{" +
                "dpi='" + dpi + '\'' +
                ", nombreCompleto='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", area='" + area + '\'' +
                ", turno='" + turno + '\'' +
                ", estado='" + estado + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}


