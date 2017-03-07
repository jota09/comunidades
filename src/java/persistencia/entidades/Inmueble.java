package persistencia.entidades;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private int codigo;
    private int comunidadcodigo;
    private int usuariocodigo;
    private String ubicacion;
    private TipoInmueble tipoinmueblecodigo;
    private short activo;

    public Inmueble() {
    }

    public Inmueble(int codigo) {
        this.codigo = codigo;
    }

    public Inmueble(int codigo, String ubicacion) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getComunidadCodigo() {
        return this.comunidadcodigo;
    }

    public void setComunidadCodigo(int comunidadcodigo) {
        this.comunidadcodigo = comunidadcodigo;
    }

    public int getUsuarioCodigo() {
        return this.usuariocodigo;
    }

    public void setUsuarioCodigo(int usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }

    public TipoInmueble getTipoInmuebleCodigo() {
        return this.tipoinmueblecodigo;
    }

    public void setTipoInmuebleCodigo(TipoInmueble tipoinmueblecodigo) {
        this.tipoinmueblecodigo = tipoinmueblecodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
