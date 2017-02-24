/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import persistencia.entidades.Comunidad;
import persistencia.entidades.Usuario;

/**
 *
 * @author manuel.alcala
 */
public class CondicionPaginado {

    private int tipo;
    private Usuario user;
    private Comunidad comunidad;
    private String estado;

    public CondicionPaginado() {
    }

    public CondicionPaginado(int tipo) {
        this.tipo = tipo;
    }

    public CondicionPaginado(int tipo, Usuario user, Comunidad comunidad, String estado) {
        this.tipo = tipo;
        this.user = user;
        this.comunidad = comunidad;
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
