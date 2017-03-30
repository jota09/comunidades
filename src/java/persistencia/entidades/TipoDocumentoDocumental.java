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
public class TipoDocumentoDocumental {
    private int codigo;
    private String nombre;

    public TipoDocumentoDocumental() {
    }

    public TipoDocumentoDocumental(int codigo) {
        this.codigo = codigo;
    }

    public TipoDocumentoDocumental(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "TipoDocumentoDocumental{" + "codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
