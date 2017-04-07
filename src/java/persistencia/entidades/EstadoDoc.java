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
public class EstadoDoc {
    private int id;
    private String nombre;
    private short activo;

    public EstadoDoc() {
    }

    public EstadoDoc(int id) {
        this.id = id;
    }

    public EstadoDoc(int id, String nombre, short activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "TipoDocumentoDoc{" + "id=" + id + ", nombre=" + nombre + ", activo=" + activo + '}';
    }
    
}