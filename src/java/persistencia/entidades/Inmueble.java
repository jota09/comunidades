package persistencia.entidades;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private int codigo;
    private int comunidadcodigo;
    private int comunidadciudadcodigo;
    private int comunidaddepartamentocodigo;
    private int comunidadpaiscodigo;
    private int usuariocodigo;
    private int tipoinmueblecodigo;
    private short activo;

    public Inmueble() {
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

    public int getComunidadCiudadCodigo() {
        return this.comunidadciudadcodigo;
    }

    public void setComunidadCiudadCodigo(int comunidadciudadcodigo) {
        this.comunidadciudadcodigo = comunidadciudadcodigo;
    }

    public int getComunidadDepartamentoCodigo() {
        return this.comunidaddepartamentocodigo;
    }

    public void setComunidadDepartamentoCodigo(int comunidaddepartamentocodigo) {
        this.comunidaddepartamentocodigo = comunidaddepartamentocodigo;
    }

    public int getComunidadPaisCodigo() {
        return this.comunidadpaiscodigo;
    }

    public void setComunidadPaisCodigo(int comunidadpaiscodigo) {
        this.comunidadpaiscodigo = comunidadpaiscodigo;
    }

    public int getUsuarioCodigo() {
        return this.usuariocodigo;
    }

    public void setUsuarioCodigo(int usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }

    public int getTipoInmuebleCodigo() {
        return this.tipoinmueblecodigo;
    }

    public void setTipoInmuebleCodigo(int tipoinmueblecodigo) {
        this.tipoinmueblecodigo = tipoinmueblecodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
