package persistencia.entidades;

import java.io.Serializable;
import java.util.List;

public class Categoria implements Serializable {

    private int codigo;
    private String nombre;
    private int codigopadre;
    private short activo;
    private List<Categoria> listaCategorias;

    public Categoria() {
    }

    public Categoria(int codigo, String nombre, int codigopadre, short activo, List<Categoria>      listaCategorias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigopadre = codigopadre;
        this.activo = activo;
        this.listaCategorias = listaCategorias;
    }

    public Categoria(int codigo) {
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

    public int getCodigoPadre() {
        return this.codigopadre;
    }

    public void setCodigoPadre(int codigopadre) {
        this.codigopadre = codigopadre;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
    
    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
}
