/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jorgmms
 */
public class Marcaje {
    
    //---- A T R I B U T O S
    private String usuario;
    private LocalDate fecha;
    private LocalTime horadeEntrada;
    private LocalTime horaDescanso1;
    private LocalTime horaDescanso2;
    private LocalTime horaSalida;
    
    // C O N S T R U C T O R
    public Marcaje(String usuario) {
        this.usuario = usuario;
        this.fecha = LocalDate.now(); // fecha por defecto
    }
    
    // G E T T E R S  AND  S E T T E R S
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public LocalTime getHoraEntrada() {
        return horadeEntrada;
    }
    
    public void setHoraEntrada(LocalTime horadeEntrada) {
        this.horadeEntrada = horadeEntrada;
    }
    
    public LocalTime getHoraDescanso1() {
        return horaDescanso1;
    }
    
    public void setHoraDescanso1(LocalTime horaDescanso1) {
        this.horaDescanso1 = horaDescanso1;
    }
    
    public LocalTime getHoraDescanso2() {
        return horaDescanso2;
    }
    
    public void setHoraDescanso2(LocalTime horaDescanso2) {
        this.horaDescanso2 = horaDescanso2;
    }
    
    public LocalTime getHoraSalida() {
        return horaSalida;
    }
    
    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
        
        
    
   
}