package persistencia.entidades;

import java.io.Serializable;

public class MultimediaAutorizacion  implements Serializable{

    private long codigo;    
    private Autorizacion autocodigo;
    private int tipomultimediacodigo;
    private short activo;
    private short destacada;
    private String extension;

    public MultimediaAutorizacion() {
    }

    public MultimediaAutorizacion(long codigo) {
        this.codigo = codigo;
    }

    public MultimediaAutorizacion(long codigo, Autorizacion autocodigo, int tipomultimediacodigo, short activo, short destacada, String extension) {
        this.codigo = codigo;
        this.autocodigo = autocodigo;
        this.tipomultimediacodigo = tipomultimediacodigo;
        this.activo = activo;
        this.destacada = destacada;
        this.extension = extension;
    }

    public MultimediaAutorizacion(Autorizacion autocodigo) {
        this.autocodigo = autocodigo;
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

    public Autorizacion getAutorizacioncodigo() {
        return autocodigo;
    }

    public void setAutorizacioncodigo(Autorizacion autocodigo) {
        this.autocodigo = autocodigo;
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
        return "Multimedia{" + "codigo=" + codigo + ", autocodigo=" + autocodigo + ", tipomultimediacodigo=" + tipomultimediacodigo + ", activo=" + activo + ", destacada=" + destacada + ", extension=" + extension + '}';
    }
    
}
