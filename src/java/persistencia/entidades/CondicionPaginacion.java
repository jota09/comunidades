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
public class CondicionPaginacion {

    private int codigo;
    private String condicion;
    private Vista vista;
    private short activaUsuario;
    private short activaComunidad;

    public CondicionPaginacion() {
    }

    public CondicionPaginacion(int codigo) {
        this.codigo = codigo;
    }

    public CondicionPaginacion(int codigo, String condicion, Vista vista, short activaUsuario, short activaComunidad) {
        this.codigo = codigo;
        this.condicion = condicion;
        this.vista = vista;
        this.activaUsuario = activaUsuario;
        this.activaComunidad = activaComunidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public Vista getVista() {
        return vista;
    }

    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public short getActivaUsuario() {
        return activaUsuario;
    }

    public void setActivaUsuario(short activaUsuario) {
        this.activaUsuario = activaUsuario;
    }

    public short getActivaComunidad() {
        return activaComunidad;
    }

    public void setActivaComunidad(short activaComunidad) {
        this.activaComunidad = activaComunidad;
    }

}
