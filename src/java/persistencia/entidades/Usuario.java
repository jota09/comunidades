package persistencia.entidades;

import java.io.Serializable;
import java.sql.Timestamp;

public class Usuario implements Serializable {

    private int codigo;
    private int codigodocumento;
    private int tipodocumentocodigo;
    private Perfil perfilcodigo;
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

    public Usuario(int codigo, int codigodocumento, int tipodocumentocodigo, Perfil perfilcodigo, String nombres, String apellidos, String correo, String celular, String telefono, String username, short activo, Timestamp creacion, Timestamp actualizacion, int edad, SeguridadUsuario listaSeguridad, Autorizacion listaAutorizacion) {
        this.codigo = codigo;
        this.codigodocumento = codigodocumento;
        this.tipodocumentocodigo = tipodocumentocodigo;
        this.perfilcodigo = perfilcodigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.celular = celular;
        this.telefono = telefono;
        this.username = username;
        this.activo = activo;
        this.creacion = creacion;
        this.actualizacion = actualizacion;
        this.edad = edad;
        this.listaSeguridad = listaSeguridad;
        this.listaAutorizacion = listaAutorizacion;
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

    public Perfil getPerfilCodigo() {
        return this.perfilcodigo;
    }

    public void setPerfilCodigo(Perfil perfilcodigo) {
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
        return "Usuario{" + "codigo=" + codigo + ", codigodocumento=" + codigodocumento + ", tipodocumentocodigo=" + tipodocumentocodigo + ", perfilcodigo=" + perfilcodigo + ", nombres=" + nombres + ", apellidos=" + apellidos + ", correo=" + correo + ", celular=" + celular + ", telefono=" + telefono + ", username=" + username + ", activo=" + activo + ", creacion=" + creacion + ", actualizacion=" + actualizacion + ", edad=" + edad + ", listaSeguridad=" + listaSeguridad + ", listaAutorizacion=" + listaAutorizacion + '}';
    }
    
}
