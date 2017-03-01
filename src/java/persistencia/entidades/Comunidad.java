package persistencia.entidades;

import java.io.Serializable;
import java.sql.Timestamp;
import org.json.simple.JSONObject;
import utilitarias.Visibilidad;

public class Comunidad implements Serializable {

    private int codigo;
    private Ciudad ciudadcodigo;
    private Departamento departamentocodigo;
    private Pais paiscodigo;
    private String nombre;
    private String direccion;
    private String telefono;
    private short activo;
    private short eliminado;
    private Timestamp creacion;
    private Visibilidad visibilidad;

    public Comunidad() {
    }

    public Comunidad(int codigo) {
        this.codigo = codigo;
    }

    public Comunidad(int codigo,String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;

    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Ciudad getCiudadCodigo() {
        return this.ciudadcodigo;
    }

    public void setCiudadCodigo(Ciudad ciudadcodigo) {
        this.ciudadcodigo = ciudadcodigo;
    }

    public Departamento getDepartamentoCodigo() {
        return this.departamentocodigo;
    }

    public void setDepartamentoCodigo(Departamento departamentocodigo) {
        this.departamentocodigo = departamentocodigo;
    }

    public Pais getPaisCodigo() {
        return this.paiscodigo;
    }

    public void setPaisCodigo(Pais paiscodigo) {
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

    public Visibilidad getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(Visibilidad visibilidad) {
        this.visibilidad = visibilidad;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("codigo", this.codigo);
        obj.put("nombre", this.nombre);
        return obj.toString();
    }

}
