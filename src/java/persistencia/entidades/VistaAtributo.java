package persistencia.entidades;

public class VistaAtributo {

    private int codigo;
    private String valor;
    private int vistacodigo;
    private int atributocodigo;
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

    public int getVistaCodigo() {
        return this.vistacodigo;
    }

    public void setVistaCodigo(int vistacodigo) {
        this.vistacodigo = vistacodigo;
    }

    public int getAtributoCodigo() {
        return this.atributocodigo;
    }

    public void setAtributoCodigo(int atributocodigo) {
        this.atributocodigo = atributocodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
