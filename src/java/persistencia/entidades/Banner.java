/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.util.Date;

/**
 *
 * @author daniel.franco
 */
public class Banner {
    private int codigo;
    private ClienteBanner cliente;
    private Date fechaInicio;
    private Date fechaFinal;
    private PrioridadBanner prioridad;
    private Date creacion;
    private int activo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ClienteBanner getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBanner cliente) {
        this.cliente = cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public PrioridadBanner getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadBanner prioridad) {
        this.prioridad = prioridad;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
