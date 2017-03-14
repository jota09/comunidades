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
public class PlantillaXComunidad {
    private int codigo;
    private PlantillaPDF plantilla;
    private Comunidad comunidad;

    public PlantillaXComunidad() {
    }

    public PlantillaXComunidad(int codigo) {
        this.codigo = codigo;
    }

    public PlantillaXComunidad(int codigo, PlantillaPDF plantilla, Comunidad comunidad) {
        this.codigo = codigo;
        this.plantilla = plantilla;
        this.comunidad = comunidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public PlantillaPDF getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(PlantillaPDF plantilla) {
        this.plantilla = plantilla;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }
    
}
