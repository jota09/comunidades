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
public class TempCargueFacturacion {

    private int documento;
    private String detalle;
    private double valor;
    private Date fecha_vencimiento;
    private Proceso proceso;

    public TempCargueFacturacion() {
    }

    public TempCargueFacturacion(int documento, String detalle, double valor, Date fecha_vencimiento, Proceso proceso) {
        this.documento = documento;
        this.detalle = detalle;
        this.valor = valor;
        this.fecha_vencimiento = fecha_vencimiento;
        this.proceso = proceso;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
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

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

}
