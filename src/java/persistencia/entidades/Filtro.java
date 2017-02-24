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
    private String condicion;
    private String tablaPK;
    private CondicionesFiltro condicionFiltro;
    private Vista vista;

    public Filtro() {
    }

    public Filtro(int codigo) {
        this.codigo = codigo;
    }

    public Filtro(int codigo, String nombre, String tabla, String campo, String condicion, CondicionesFiltro condicionesFiltro, Vista vista) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tabla = tabla;
        this.campo = campo;
        this.condicion = condicion;
        this.condicionFiltro = condicionesFiltro;
        this.vista = vista;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
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

    public CondicionesFiltro getCondicionFiltro() {
        return condicionFiltro;
    }

    public void setCondicionFiltro(CondicionesFiltro condicionFiltro) {
        this.condicionFiltro = condicionFiltro;
    }

    public Vista getVista() {
        return vista;
    }

    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public String getTablaPK() {
        return tablaPK;
    }

    public void setTablaPK(String tablaPK) {
        this.tablaPK = tablaPK;
    }

}
