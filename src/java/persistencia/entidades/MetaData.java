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
    private String pkTabla;
    private String columna;
    private int codVista;

    public MetaData() {
    }

    public MetaData(int codigo) {
        this.codigo = codigo;
    }

    public MetaData(int codigo, String columna, String esquema, String tabla, int codVista) {
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

    public int getCodVista() {
        return codVista;
    }

    public void setCodVista(int codVista) {
        this.codVista = codVista;
    }

    public String getPkTabla() {
        return pkTabla;
    }

    public void setPkTabla(String pkTabla) {
        this.pkTabla = pkTabla;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

}
