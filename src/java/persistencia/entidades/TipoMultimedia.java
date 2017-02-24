package persistencia.entidades;

import java.io.Serializable;
import org.json.simple.JSONObject;

public class TipoMultimedia implements Serializable {

    private int codigo;
    private String nombre;
    private String extension;
    private short activo;

    public TipoMultimedia(int codigo) {
        this.codigo = codigo;
    }

    public TipoMultimedia(int codigo, String nombre, String extension, short activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.extension = extension;
        this.activo = activo;
    }

    public TipoMultimedia() {
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

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("codigo", this.codigo);
        obj.put("nombre", this.nombre);
        return obj.toString();
    }
}
