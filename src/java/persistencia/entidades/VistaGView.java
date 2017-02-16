/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.io.Serializable;

/**
 *
 * @author manuel.alcala
 */
public class VistaGView implements Serializable {
    private int codigo;
    private String nombre;

    public VistaGView() {
    }
    
    public VistaGView(int codigo) {
        this.codigo = codigo;
    }

    public VistaGView(int codigo, String nombre) {
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
    
}
