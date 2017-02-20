/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author ALEJO
 */
public class CondicionesFiltro {
    private int codigo;
    private String condicion;
    private int parametros;

    public CondicionesFiltro() {
    }

    public CondicionesFiltro(int codigo) {
        this.codigo = codigo;
    }

    public CondicionesFiltro(int codigo, String condicion,int parametros) {
        this.codigo = codigo;
        this.condicion = condicion;
        this.parametros=parametros;
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

    public int getParametros() {
        return parametros;
    }

    public void setParametros(int parametros) {
        this.parametros = parametros;
    }
    
    
}
