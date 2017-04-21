package persistencia.entidades;

import java.io.Serializable;
import org.json.simple.JSONObject;

public class Perfil implements Serializable {

    private int codigo;
    private String nombre;
    private Comunidad comunidad;
    private short activo;

    public Perfil() {
    }

    public Perfil(int codigo) {
        this.codigo = codigo;
    }

    public Perfil(int codigo, String nombre, Comunidad comunidad, short activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.comunidad = comunidad;
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

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

      @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("codigo", this.codigo);
        obj.put("nombre", this.nombre);
        return obj.toString();
    }
    
    
    
}
