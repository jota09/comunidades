/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author Jesus.Ramos
 */
public class ContenidoDoc {
    private int id;
    private String valor;
    private short activo;
    private ComponenteDoc componente;

    public ContenidoDoc() {
    }

    public ContenidoDoc(int id) {
        this.id = id;
    }

    public ContenidoDoc(int id, String valor, short activo, ComponenteDoc componente) {
        this.id = id;
        this.valor = valor;
        this.activo = activo;
        this.componente = componente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public ComponenteDoc getComponente() {
        return componente;
    }

    public void setComponente(ComponenteDoc componente) {
        this.componente = componente;
    }

    @Override
    public String toString() {
        return "ContenidoDoc{" + "id=" + id + ", valor=" + valor + ", activo=" + activo + ", componente=" + componente + '}';
    }

}
