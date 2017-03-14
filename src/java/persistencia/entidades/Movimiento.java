/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.util.Date;

/**
 *
 * @author manuel.alcala
 */
public class Movimiento {

    private int codigo;
    private String detalle;
    private double valor;
    private Date fechaVencimiento;
    private Factura factura;

    public Movimiento() {
    }

    public Movimiento(int codigo) {
        this.codigo = codigo;
    }

    public Movimiento(int codigo, String detalle, double valor, Factura factura,Date fechaVencimiento) {
        this.codigo = codigo;
        this.detalle = detalle;
        this.valor = valor;
        this.factura = factura;
        this.fechaVencimiento=fechaVencimiento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    

}
