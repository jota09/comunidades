package persistencia.entidades;

import java.io.Serializable;

public class Multimedia  implements Serializable{

    private long codigo;    
    private Articulo articulocodigo;
    private int tipomultimediacodigo;
    private short activo;
    private short destacada;
    private String extension;
    private TipoMultimedia tipo;

    public Multimedia() {
    }

    public Multimedia(long codigo) {
        this.codigo = codigo;
    }

    public Multimedia(long codigo, Articulo articulocodigo, int tipomultimediacodigo, short activo, short destacada, String extension) {
        this.codigo = codigo;
        this.articulocodigo = articulocodigo;
        this.tipomultimediacodigo = tipomultimediacodigo;
        this.activo = activo;
        this.destacada = destacada;
        this.extension = extension;
    }

    public Multimedia(Articulo articulocodigo) {
        this.articulocodigo = articulocodigo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
        
    public long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Articulo getArticulocodigo() {
        return articulocodigo;
    }

    public TipoMultimedia getTipo() {
        return tipo;
    }

    public void setTipo(TipoMultimedia tipo) {
        this.tipo = tipo;
    }

    public void setArticulocodigo(Articulo articulocodigo) {
        this.articulocodigo = articulocodigo;
    }
    
    public int getTipoMultimediaCodigo() {
        return this.tipomultimediacodigo;
    }

    public void setTipoMultimediaCodigo(int tipomultimediacodigo) {
        this.tipomultimediacodigo = tipomultimediacodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public short getDestacada() {
        return destacada;
    }

    public void setDestacada(short destacada) {
        this.destacada = destacada;
    }

    @Override
    public String toString() {
        return "Multimedia{" + "codigo=" + codigo + ", articulocodigo=" + articulocodigo + ", tipomultimediacodigo=" + tipomultimediacodigo + ", activo=" + activo + ", destacada=" + destacada + ", extension=" + extension + '}';
    }
    
}
