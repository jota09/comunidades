package persistencia.entidades;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comunidad implements Serializable {

    private int codigo;
    private int ciudadcodigo;
    private int departamentocodigo;
    private int paiscodigo;
    private String nombre;
    private String direccion;
    private String telefono;
    private short activo;
    private short eliminado;
    private Timestamp creacion;

    public Comunidad() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCiudadCodigo() {
        return this.ciudadcodigo;
    }

    public void setCiudadCodigo(int ciudadcodigo) {
        this.ciudadcodigo = ciudadcodigo;
    }

    public int getDepartamentoCodigo() {
        return this.departamentocodigo;
    }

    public void setDepartamentoCodigo(int departamentocodigo) {
        this.departamentocodigo = departamentocodigo;
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public short getEliminado() {
        return this.eliminado;
    }

    public void setEliminado(short eliminado) {
        this.eliminado = eliminado;
    }

    public Timestamp getCreacion() {
        return this.creacion;
    }

    public void setCreacion(Timestamp creacion) {
        this.creacion = creacion;
    }
}
