package persistencia.entidades;

import java.sql.Timestamp;

public class SeguridadUsuario {

    private int codigo;
    private String contrasena;
    private String ipultimasesion;
    private Timestamp fechaultimasesion;
    private short activo;
    private String codigoseguridad;
    private int usuariocodigo;

    public SeguridadUsuario() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIpUltimaSesion() {
        return this.ipultimasesion;
    }

    public void setIpUltimaSesion(String ipultimasesion) {
        this.ipultimasesion = ipultimasesion;
    }

    public Timestamp getFechaUltimaSesion() {
        return this.fechaultimasesion;
    }

    public void setFechaUltimaSesion(Timestamp fechaultimasesion) {
        this.fechaultimasesion = fechaultimasesion;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public String getCodigoSeguridad() {
        return this.codigoseguridad;
    }

    public void setCodigoSeguridad(String codigoseguridad) {
        this.codigoseguridad = codigoseguridad;
    }

    public int getUsuarioCodigo() {
        return this.usuariocodigo;
    }

    public void setUsuarioCodigo(int usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }
}
