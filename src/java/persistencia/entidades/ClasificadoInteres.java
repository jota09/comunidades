package persistencia.entidades;

public class ClasificadoInteres {

    private int codigo;
    private int usuariocodigo;
    private Articulo clasificado;
    private short activo;

    public ClasificadoInteres() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUsuarioCodigo() {
        return this.usuariocodigo;
    }

    public void setUsuarioCodigo(int usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }

    public Articulo getClasificado() {
        return this.clasificado;
    }

    public void setClasificadoCodigo(Articulo clasificado) {
        this.clasificado = clasificado;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
