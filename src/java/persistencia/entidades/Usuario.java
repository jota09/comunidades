package persistencia.entidades;

import java.sql.Timestamp;

public class Usuario {

    private int codigo;
    private int codigodocumento;
    private int tipodocumentocodigo;
    private int perfilcodigo;
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String telefono;
    private String username;
    private short activo;
    private Timestamp creacion;
    private Timestamp actualizacion;
    private int edad;
    private SeguridadUsuario listaSeguridad;
    private Autorizacion listaAutorizacion;

    public Usuario() {
    }

    public Usuario(int codigo) {
        this.codigo = codigo;
    }

    
    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoDocumento() {
        return this.codigodocumento;
    }

    public void setCodigoDocumento(int codigodocumento) {
        this.codigodocumento = codigodocumento;
    }

    public int getTipoDocumentoCodigo() {
        return this.tipodocumentocodigo;
    }

    public void setTipoDocumentoCodigo(int tipodocumentocodigo) {
        this.tipodocumentocodigo = tipodocumentocodigo;
    }

    public int getPerfilCodigo() {
        return this.perfilcodigo;
    }

    public void setPerfilCodigo(int perfilcodigo) {
        this.perfilcodigo = perfilcodigo;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public Timestamp getCreacion() {
        return this.creacion;
    }

    public void setCreacion(Timestamp creacion) {
        this.creacion = creacion;
    }

    public Timestamp getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Timestamp actualizacion) {
        this.actualizacion = actualizacion;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
        public SeguridadUsuario getListaSeguridad() {
        return listaSeguridad;
    }

    public void setListaSeguridad(SeguridadUsuario listaSeguridad) {
        this.listaSeguridad = listaSeguridad;
    }

    public Autorizacion getListaAutorizacion() {
        return listaAutorizacion;
    }

    public void setListaAutorizacion(Autorizacion listaAutorizacion) {
        this.listaAutorizacion = listaAutorizacion;
    }

    @Override
    public String toString() {
        return codigo+" "+nombres+" "+apellidos;
    }
}
