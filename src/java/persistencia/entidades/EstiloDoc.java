/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author Jesus.Ramos
 */
public class EstiloDoc {
    private int id;
    private String style;

    public EstiloDoc(int id) {
        this.id = id;
    }

    public EstiloDoc(int id, String style) {
        this.id = id;
        this.style = style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "EstiloDoc{" + "id=" + id + ", style=" + style + '}';
    }
    
}
