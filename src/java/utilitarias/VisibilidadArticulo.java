/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

/**
 *
 * @author manuel.alcala
 */
public class VisibilidadArticulo {
    private Short visibilidad;

    public VisibilidadArticulo() {
    }

    public VisibilidadArticulo(short visibilidad) {
        this.visibilidad = visibilidad;
    }

    public int getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(short visibilidad) {
        this.visibilidad = visibilidad;
    }
    
}
