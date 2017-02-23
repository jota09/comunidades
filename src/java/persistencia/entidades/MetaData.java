/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.util.List;

/**
 *
 * @author manuel.alcala
 */
public class MetaData {

    private int codigo;
    private String esquema;
    private String tabla;
    private int codVista;
    private List<String> columna;

    public MetaData() {
    }

    public MetaData(int codigo) {
        this.codigo = codigo;
    }

    public MetaData(int codigo, String esquema, String tabla, List<String> columna, int codVista) {
        this.codigo = codigo;
        this.esquema = esquema;
        this.tabla = tabla;
        this.columna = columna;
        this.codVista = codVista;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public List<String> getColumna() {
        return columna;
    }

    public void setColumna(List<String> columna) {
        this.columna = columna;
    }

    public int getCodVista() {
        return codVista;
    }

    public void setCodVista(int codVista) {
        this.codVista = codVista;
    }

}
