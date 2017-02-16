package persistencia.entidades;

import java.util.List;

public class TipoArticulo {

    private int codigo;
    private String nombre;
    private short activo;
    private List<Articulo> listaArticulos;
    private String rango;

    public TipoArticulo() {
    }

    public TipoArticulo(int codigo, String nombre, short activo, List<Articulo> listaArticulos, String rango) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.activo = activo;
        this.listaArticulos = listaArticulos;
        this.rango = rango;
    }

    public TipoArticulo(int codigo) {
        this.codigo = codigo;
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

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
    
    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    @Override
    public String toString() {
        return "TipoArticulo{" + "codigo=" + codigo + ", nombre=" + nombre + ", activo=" + activo + ", listaArticulos=" + listaArticulos + ", rango=" + rango + '}';
    }
}
