package persistencia.entidades;

public class RecursoVista {

    private int codigo;
    private int recursocodigo;
    private int vistacodigo;

    public RecursoVista() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getRecursoCodigo() {
        return this.recursocodigo;
    }

    public void setRecursoCodigo(int recursocodigo) {
        this.recursocodigo = recursocodigo;
    }

    public int getVistaCodigo() {
        return this.vistacodigo;
    }

    public void setVistaCodigo(int vistacodigo) {
        this.vistacodigo = vistacodigo;
    }
}
