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
public class Registro {

    private int codigo;
    private String codigoGenerado;
    private Comunidad comunidad;
    private String correo;
    private Timestamp fechaVencimiento;

    public Registro() {
    }

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public Registro(int codigo, String codigoGenerado, Comunidad comunidad, String correo, Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
        this.codigo = codigo;
        this.codigoGenerado = codigoGenerado;
        this.comunidad = comunidad;
        this.correo = correo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigoGenerado() {
        return codigoGenerado;
    }

    public void setCodigoGenerado(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
