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

    private Usuario user;
    private Comunidad comunidad;
    private String condicion;

    public CondicionPaginado() {
    }

    public CondicionPaginado(int tipo, Usuario user, Comunidad comunidad, String condicion) {

        this.user = user;
        this.comunidad = comunidad;
        this.condicion = condicion;
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

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return "CondicionPaginado{" + "user=" + user + ", comunidad=" + comunidad + ", condicion=" + condicion + '}';
    }

}
