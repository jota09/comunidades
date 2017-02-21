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
public class TipoError {

    private int codigo;
    private String tipo;


    public TipoError() {
    }

    public TipoError(int codigo) {
        this.codigo = codigo;
    }

    public TipoError(int codigo, String tipo) {
        this.codigo = codigo;
        this.tipo = tipo;

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
