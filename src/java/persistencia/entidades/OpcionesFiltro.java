/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author manuel.alcala
 */
public class OpcionesFiltro {
    private int codigo;
    private String nombre;
    private String valor;
    private Filtro filtro;

    public OpcionesFiltro() {
    }

    public OpcionesFiltro(int codigo) {
        this.codigo = codigo;
    }

    public OpcionesFiltro(int codigo, String nombre, String valor, Filtro filtro) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
        this.filtro = filtro;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }
    
    
}
