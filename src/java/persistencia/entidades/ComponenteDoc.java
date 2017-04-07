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
public class ComponenteDoc {
    private int id;
    private String nombre;
    private String referencia;
    private ComponenteDoc componente;
    private TipoDocumentoDoc tipoD;
    private TipoCompDoc tipo;

    public ComponenteDoc() {
    }

    public ComponenteDoc(int id) {
        this.id = id;
    }

    public ComponenteDoc(int id, String nombre, String referencia, TipoDocumentoDoc tipoD, ComponenteDoc componente, TipoCompDoc tipo) {
        this.id = id;
        this.nombre = nombre;
        this.referencia = referencia;
        this.tipoD = tipoD;
        this.componente = componente;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public TipoDocumentoDoc getTipoD() {
        return tipoD;
    }

    public void setTipoD(TipoDocumentoDoc tipoD) {
        this.tipoD = tipoD;
    }

    public ComponenteDoc getComponente() {
        return componente;
    }

    public void setComponente(ComponenteDoc componente) {
        this.componente = componente;
    }

    public TipoCompDoc getTipo() {
        return tipo;
    }

    public void setTipo(TipoCompDoc tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ComponenteDoc{" + "id=" + id + ", nombre=" + nombre + ", referencia=" + referencia + ", tipoD=" + tipoD + ", componente=" + componente + ", tipo=" + tipo + '}';
    }

}
