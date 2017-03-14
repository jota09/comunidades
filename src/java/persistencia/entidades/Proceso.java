/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.sql.Timestamp;

/**
 *
 * @author manuel.alcala
 */
public class Proceso {

    private int codigo;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Comunidad comunidad;
    private EventoProceso eventoProceso;
    private Usuario usuarioResponsable;
    private PlantillaXComunidad plantillaXComunidad;

    public Proceso() {
    }

    public Proceso(int codigo) {
        this.codigo = codigo;
    }

    public Proceso(int codigo, Timestamp fechaInicio, Timestamp fechaFin, Comunidad comunidad, EventoProceso eventoProceso, Usuario usuarioResponsable, PlantillaXComunidad plantillaXComunidad) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.comunidad = comunidad;
        this.eventoProceso = eventoProceso;
        this.usuarioResponsable = usuarioResponsable;
        this.plantillaXComunidad = plantillaXComunidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public EventoProceso getEventoProceso() {
        return eventoProceso;
    }

    public void setEventoProceso(EventoProceso eventoProceso) {
        this.eventoProceso = eventoProceso;
    }

    public Usuario getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(Usuario usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public PlantillaXComunidad getPlantillaXComunidad() {
        return plantillaXComunidad;
    }

    public void setPlantillaXComunidad(PlantillaXComunidad plantillaXComunidad) {
        this.plantillaXComunidad = plantillaXComunidad;
    }

}
