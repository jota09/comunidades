/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author daniel.franco
 */
public class ZonaComun {

    private int codigo;
    private String nombre;
    private Comunidad comunidad;
    private double alquiler;
    private String descripcion;
    private Tiempo tiempo;

    public ZonaComun() {
    }

    public ZonaComun(int codigo) {
        this.codigo = codigo;
    }

    public ZonaComun(int codigo, String nombre, Comunidad comunidad, double alquiler, String descripcion, Tiempo tiempo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.comunidad = comunidad;
        this.alquiler = alquiler;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
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

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(double alquiler) {
        this.alquiler = alquiler;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
