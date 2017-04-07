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
    private DocumentoDoc documento;
    private Usuario user;

    public ContenidoDoc() {
    }

    public ContenidoDoc(int id) {
        this.id = id;
    }

    public ContenidoDoc(int id, String valor, short activo, ComponenteDoc componente, DocumentoDoc documento, Usuario user) {
        this.id = id;
        this.valor = valor;
        this.activo = activo;
        this.componente = componente;
        this.documento = documento;
        this.user = user;
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

    public DocumentoDoc getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDoc documento) {
        this.documento = documento;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ContenidoDoc{" + "id=" + id + ", valor=" + valor + ", activo=" + activo + ", componente=" + componente + ", documento=" + documento + ", user=" + user + '}';
    }

}
