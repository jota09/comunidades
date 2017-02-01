/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author Jesus.Ramos
 */
public class Estructura {
    private int codigo;
    private String referencia;
    private String direccion;
    private String descripcion;

    public Estructura(int codigo) {
        this.codigo = codigo;
    }

    public Estructura(int codigo, String referencia, String direccion, String descripcion) {
        this.codigo = codigo;
        this.referencia = referencia;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDireccion() {
        return direccion;
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

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Estructura{" + "codigo=" + codigo + ", referencia=" + referencia + ", direccion=" + direccion + ", descripcion=" + descripcion + '}';
    }
    
}
