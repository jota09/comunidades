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
public class EstadoAutorizacion {
    
    private int codigo;
    private String nombre;
    private short activo;

    public EstadoAutorizacion(int codigo) {
        this.codigo = codigo;
    }

    public EstadoAutorizacion(String nombre) {
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

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "EstadoAutorizacion{" + "codigo=" + codigo + ", nombre=" + nombre + ", activo=" + activo + '}';
    }
    
}