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
public class LogXProceso {

    private int codigo;
    private String descripcion;
    private Proceso proceso;
    private Timestamp fecha;

    public LogXProceso() {
    }

    public LogXProceso(int codigo) {
        this.codigo = codigo;
    }

    public LogXProceso(int codigo, String descripcion, Proceso proceso, Timestamp fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.proceso = proceso;
        this.fecha = fecha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
