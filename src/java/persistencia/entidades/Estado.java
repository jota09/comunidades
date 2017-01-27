/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author ferney.medina
 */
public class Estado {
    private int codigo;
    private String nombre;
    private String observacion;
    private EventoReserva eventoReserva;
    private Articulo articulo;

    public Estado() {
    }

    public Estado(int codigo) {
        this.codigo = codigo;
    }

    public Estado(int codigo, String nombre, String observacion, EventoReserva eventoReserva, Articulo articulo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.observacion = observacion;
        this.eventoReserva = eventoReserva;
        this.articulo = articulo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public EventoReserva getEventoReserva() {
        return eventoReserva;
    }

    public void setEventoReserva(EventoReserva eventoReserva) {
        this.eventoReserva = eventoReserva;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    
    
}