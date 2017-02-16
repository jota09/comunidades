package persistencia.entidades;

import java.io.Serializable;

public class Departamento implements Serializable {

    private int codigo;
    private int paiscodigo;
    private String nombre;
    private short activo;

    public Departamento() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getPaisCodigo() {
        return this.paiscodigo;
    }

    public void setPaisCodigo(int paiscodigo) {
        this.paiscodigo = paiscodigo;
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
}
