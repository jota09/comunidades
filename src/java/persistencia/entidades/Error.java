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
public class Error {

    private int codigo;
    private String clase;
    private String metodo;
    private Timestamp fecha;
    private TipoError tipoError;
    private String descripcion;

    public Error() {
    }

    public Error(int codigo) {
        this.codigo = codigo;
    }

    public Error(int codigo, String clase, String metodo, TipoError tipoError,Timestamp fecha,String descripcion) {
        this.codigo = codigo;
        this.clase = clase;
        this.metodo = metodo;
        this.tipoError = tipoError;
        this.fecha=fecha;
        this.descripcion=descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public TipoError getTipoError() {
        return tipoError;
    }

    public void setTipoError(TipoError tipoError) {
        this.tipoError = tipoError;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
