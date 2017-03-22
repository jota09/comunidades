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
public class Factura {

    private int codigo;
    private Proceso proceso;
    private Usuario usuario;
    private String numFactura;

    public Factura() {
    }

    public Factura(int codigo) {
        this.codigo = codigo;
    }

    public Factura(int codigo, Proceso proceso, Usuario usuario,String numFactura) {
        this.codigo = codigo;
        this.proceso = proceso;
        this.usuario = usuario;
        this.numFactura=numFactura;
      
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

}
