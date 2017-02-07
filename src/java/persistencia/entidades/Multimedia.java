package persistencia.entidades;

public class Multimedia {

    private int codigo;
    private int articulocodigo;
    private int tipomultimediacodigo;
    private short activo;

    public Multimedia() {
    }

    public Multimedia(int codigo) {
        this.codigo = codigo;
    }

    public Multimedia(int codigo, int articulocodigo, int tipomultimediacodigo, short activo) {
        this.codigo = codigo;
        this.articulocodigo = articulocodigo;
        this.tipomultimediacodigo = tipomultimediacodigo;
        this.activo = activo;
    }

    
    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
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
}
