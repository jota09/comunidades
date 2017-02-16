package persistencia.entidades;

import java.io.Serializable;

public class RecursoVista implements Serializable {

    private int codigo;
    private Recurso recursocodigo;
    private Vista vistacodigo;

    public RecursoVista() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Recurso getRecursoCodigo() {
        return this.recursocodigo;
    }

    public void setRecursoCodigo(Recurso recursocodigo) {
        this.recursocodigo = recursocodigo;
    }

    public Vista getVistaCodigo() {
        return this.vistacodigo;
    }

    public void setVistaCodigo(Vista vistacodigo) {
        this.vistacodigo = vistacodigo;
    }
}
