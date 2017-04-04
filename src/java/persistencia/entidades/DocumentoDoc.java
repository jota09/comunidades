/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

import java.util.Date;

/**
 *
 * @author Jesus.Ramos
 */
public class DocumentoDoc {
    private int id;
    private String version;
    private Date publicado;
    private Usuario user;
    private EstadoDoc estado;
    private EstiloDoc estilo;

    public DocumentoDoc() {
    }

    public DocumentoDoc(int id) {
        this.id = id;
    }

    public DocumentoDoc(int id, String version, Date publicado, Usuario user, EstadoDoc estado, EstiloDoc estilo) {
        this.id = id;
        this.version = version;
        this.publicado = publicado;
        this.user = user;
        this.estado = estado;
        this.estilo = estilo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getPublicado() {
        return publicado;
    }

    public void setPublicado(Date publicado) {
        this.publicado = publicado;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public EstadoDoc getEstado() {
        return estado;
    }

    public void setEstado(EstadoDoc estado) {
        this.estado = estado;
    }

    public EstiloDoc getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloDoc estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "DocumentoDoc{" + "id=" + id + ", version=" + version + ", publicado=" + publicado + ", user=" + user + ", estado=" + estado + ", estilo=" + estilo + '}';
    }
    
}
