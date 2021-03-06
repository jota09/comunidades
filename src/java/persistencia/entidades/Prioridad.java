/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 *
 * @author Jesus.Ramos
 */
public class Prioridad implements Serializable {
    private int codigo;
    private String nombre;
    private int valor;
    private short activo;

    public Prioridad(int codigo) {
        this.codigo = codigo;
    }    

    public Prioridad() {
    }

    public Prioridad(int codigo, String nombre, int valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Prioridad(int codigo, String nombre, int valor, short activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
        this.activo = activo;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("codigo", this.codigo);
        obj.put("nombre", this.nombre);
        return obj.toString();
    }
    
    
    
}
