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
public class DocumentoBorrador {
    
    private long codigo;
    private String remitente;
    private String destinatario;
    private String asunto;
    private String descripcion;
    private String responsable;
    private TipoDocumentoDocumental tipoDocumento;
    private Comunidad comunidad;
    private Usuario usuario;

    public DocumentoBorrador() {
    }

    public DocumentoBorrador(long codigo) {
        this.codigo = codigo;
    }

    public DocumentoBorrador(long codigo, String remitente, String destinatario, String asunto, String descripcion, String responsable, TipoDocumentoDocumental tipoDocumento, Comunidad comunidad, Usuario usuario) {
        this.codigo = codigo;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.tipoDocumento = tipoDocumento;
        this.comunidad = comunidad;
        this.usuario = usuario;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public TipoDocumentoDocumental getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDocumental tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "DocumentoBorrador{" + "codigo=" + codigo + ", remitente=" + remitente + ", destinatario=" + destinatario + ", asunto=" + asunto + ", descripcion=" + descripcion + ", responsable=" + responsable + ", tipoDocumento=" + tipoDocumento + ", comunidad=" + comunidad + ", usuario=" + usuario + '}';
    }
    
    
}
