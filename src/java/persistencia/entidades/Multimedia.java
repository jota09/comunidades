package persistencia.entidades;

import java.io.Serializable;

public class Multimedia  implements Serializable{

    private long codigo;
    private int articulocodigo;
    private int tipomultimediacodigo;
    private short activo;
    private short destacada;

    public Multimedia() {
    }

    public Multimedia(int codigo) {
        this.codigo = codigo;
    }

    public Multimedia(int codigo, int articulocodigo, int tipomultimediacodigo, short activo, short destacada) {
        this.codigo = codigo;
        this.articulocodigo = articulocodigo;
        this.tipomultimediacodigo = tipomultimediacodigo;
        this.activo = activo;
        this.destacada = destacada;
    }
    
    public long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public int getArticuloCodigo() {
        return this.articulocodigo;
    }

    public void setArticuloCodigo(int articulocodigo) {
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
    
}
