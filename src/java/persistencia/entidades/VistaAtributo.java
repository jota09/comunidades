package persistencia.entidades;

public class VistaAtributo {

    private int codigo;
    private String valor;
    private Vista vistacodigo;
    private Atributo atributocodigo;
    private short activo;

    public VistaAtributo() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Vista getVistaCodigo() {
        return this.vistacodigo;
    }

    public void setVistaCodigo(Vista vistacodigo) {
        this.vistacodigo = vistacodigo;
    }

    public Atributo getAtributoCodigo() {
        return this.atributocodigo;
    }

    public void setAtributoCodigo(Atributo atributocodigo) {
        this.atributocodigo = atributocodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
