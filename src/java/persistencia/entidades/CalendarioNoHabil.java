package persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

public class CalendarioNoHabil implements Serializable {

    private int codigo;
    private Date dianohabil;
    private String descripcion;
    private short activo;

    public CalendarioNoHabil() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getDiaNoHabil() {
        return this.dianohabil;
    }

    public void setDiaNoHabil(Date dianohabil) {
        this.dianohabil = dianohabil;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
