package persistencia.entidades;

/**
 *
 * @author manuel.alcala
 */
public class Filtro {

    private int codigo;
    private String nombre;
    private String tabla;
    private String campo;

    public Filtro() {
    }

    public Filtro(int codigo) {
        this.codigo = codigo;
    }

    public Filtro(int codigo, String nombre, String tabla, String campo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tabla = tabla;
        this.campo = campo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

}
