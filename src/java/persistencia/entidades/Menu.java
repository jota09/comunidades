package persistencia.entidades;

import java.util.List;

public class Menu {

    private int codigo;
    private int codigopadre;
    private String nombre;
    private String url;
    private short activo;
    private List<Menu> listaMenu;


    public Menu() {
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
}
