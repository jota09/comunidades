/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.io.Serializable;

/**
 *
 * @author Jesus.Ramos
 */
public class Estructura implements Serializable {
    private int codigo;
    private String referencia;
    private String valor;
    private String descripcion;

    public Estructura(int codigo) {
        this.codigo = codigo;
    }

    public Estructura() {
    }
    
    public Estructura(int codigo, String referencia, String valor, String descripcion) {
        this.codigo = codigo;
        this.referencia = referencia;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public Estructura(String referencia) {
        this.referencia = referencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getValor() {
        return valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setValor(String direccion) {
        this.valor = direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Estructura{" + "codigo=" + codigo + ", referencia=" + referencia + ", valor=" + valor + ", descripcion=" + descripcion + '}';
    }
        
}
