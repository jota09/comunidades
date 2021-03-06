/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author daniel.franco
 */
public class Reserva {

    private int codigo;
    private Usuario usuarioCodigo;
    private ZonaComun zonaCodigo;
    private Comunidad comunidadCodigo;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private double costo;
    private String descripcion;
    private Timestamp creacion;
    private Timestamp actualizacion;
    private ReservaEstado estadoCodigo;

    public Reserva() {
    }

    public Reserva(int codigo) {
        this.codigo = codigo;
    }

    public Reserva(int codigo, Usuario usuarioCodigo, ZonaComun zonaCodigo, Comunidad comunidadCodigo, Timestamp fechaInicio, Timestamp fechaFin, double costo, String descripcion, Timestamp creacion, Timestamp actualizacion, ReservaEstado estadoCodigo) {
        this.codigo = codigo;
        this.usuarioCodigo = usuarioCodigo;
        this.zonaCodigo = zonaCodigo;
        this.comunidadCodigo = comunidadCodigo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costo = costo;
        this.descripcion = descripcion;
        this.creacion = creacion;
        this.actualizacion = actualizacion;
        this.estadoCodigo = estadoCodigo;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(Usuario usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public ZonaComun getZonaCodigo() {
        return zonaCodigo;
    }

    public void setZonaCodigo(ZonaComun zonaCodigo) {
        this.zonaCodigo = zonaCodigo;
    }

    public Comunidad getComunidadCodigo() {
        return comunidadCodigo;
    }

    public void setComunidadCodigo(Comunidad comunidadCodigo) {
        this.comunidadCodigo = comunidadCodigo;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getCreacion() {
        return creacion;
    }

    public void setCreacion(Timestamp creacion) {
        this.creacion = creacion;
    }

    public Timestamp getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Timestamp actualizacion) {
        this.actualizacion = actualizacion;
    }

    public ReservaEstado getEstadoCodigo() {
        return estadoCodigo;
    }

    public void setEstadoCodigo(ReservaEstado estadoCodigo) {
        this.estadoCodigo = estadoCodigo;
    }
}
