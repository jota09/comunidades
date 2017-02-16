package persistencia.entidades;

import java.io.Serializable;

public class PerfilMenu implements Serializable {

    private int perfilcodigo;
    private int menucodigo;
    private short activo;

    public PerfilMenu() {
    }

    public int getPerfilCodigo() {
        return this.perfilcodigo;
    }

    public void setPerfilCodigo(int perfilcodigo) {
        this.perfilcodigo = perfilcodigo;
    }

    public int getMenuCodigo() {
        return this.menucodigo;
    }

    public void setMenuCodigo(int menucodigo) {
        this.menucodigo = menucodigo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
