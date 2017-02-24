package persistencia.entidades;

import java.io.Serializable;
import java.util.List;
import org.json.simple.JSONObject;

public class Menu implements Serializable {

    private int codigo;
    private int codigopadre;
    private String nombre;
    private String url;
    private short activo;
    private List<Menu> listaMenu;

    public Menu() {
    }

    public Menu(int codigo) {
        this.codigo = codigo;
    }

    public Menu(int codigo, int codigopadre, String nombre, String url, short activo, List<Menu> listaMenu) {
        this.codigo = codigo;
        this.codigopadre = codigopadre;
        this.nombre = nombre;
        this.url = url;
        this.activo = activo;
        this.listaMenu = listaMenu;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoPadre() {
        return this.codigopadre;
    }

    public void setCodigoPadre(int codigopadre) {
        this.codigopadre = codigopadre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public List<Menu> getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(List<Menu> listaMenu) {
        this.listaMenu = listaMenu;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("codigo", this.codigo);
        obj.put("nombre", this.nombre);
        return obj.toString();
    }
}
