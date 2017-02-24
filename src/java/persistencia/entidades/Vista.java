package persistencia.entidades;

import java.io.Serializable;

public class Vista implements Serializable {

    private int codigo;
    private String nombre;
    private String url;
    private short activo;

    public Vista() {
    }

    public Vista(int codigo) {
        this.codigo = codigo;
    }

    public Vista(int codigo, String nombre, String url, short activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.url = url;
        this.activo = activo;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
