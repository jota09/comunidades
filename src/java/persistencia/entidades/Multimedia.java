package persistencia.entidades;

public class Multimedia {

    private int codigo;
    private int articulocodigo;
    private String nombre;
    private int tipomultimediacodigo;
    private short activo;

    public Multimedia(int codigo) {
        this.codigo = codigo;
    }

    public Multimedia(int codigo, int articulocodigo, String nombre, int tipomultimediacodigo, short activo) {
        this.codigo = codigo;
        this.articulocodigo = articulocodigo;
        this.nombre = nombre;
        this.tipomultimediacodigo = tipomultimediacodigo;
        this.activo = activo;
    }

    public Multimedia() {
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
