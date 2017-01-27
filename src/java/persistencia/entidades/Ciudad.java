package persistencia.entidades;

public class Ciudad {

    private int codigo;
    private int departamentocodigo;
    private int paiscodigo;
    private String nombre;
    private short activo;

    public Ciudad() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
