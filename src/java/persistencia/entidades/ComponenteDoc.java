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
    private String html;
    private DocumentoDoc documento;
    private ComponenteDoc componente;
    private EstadoCompDoc estado;

    public ComponenteDoc() {
    }

    public ComponenteDoc(int id) {
        this.id = id;
    }

    public ComponenteDoc(int id, String nombre, String referencia, String html, DocumentoDoc documento, ComponenteDoc componente, EstadoCompDoc estado) {
        this.id = id;
        this.nombre = nombre;
        this.referencia = referencia;
        this.html = html;
        this.documento = documento;
        this.componente = componente;
        this.estado = estado;
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public DocumentoDoc getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDoc documento) {
        this.documento = documento;
    }

    public ComponenteDoc getComponente() {
        return componente;
    }

    public void setComponente(ComponenteDoc componente) {
        this.componente = componente;
    }

    public EstadoCompDoc getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompDoc estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ComponenteDoc{" + "id=" + id + ", nombre=" + nombre + ", referencia=" + referencia + ", html=" + html + ", documento=" + documento + ", componente=" + componente + ", estado=" + estado + '}';
    }
    
}
