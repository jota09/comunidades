package persistencia.entidades;

public class TipoInmueble {

    private int codigo;
    private String nombre;
    private double preciounidad;
    private int cantidad;
    private short activo;

    public TipoInmueble() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnidad() {
        return this.preciounidad;
    }

    public void setPrecioUnidad(double preciounidad) {
        this.preciounidad = preciounidad;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
