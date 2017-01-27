/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import java.util.ArrayList;
import java.util.List;
import persistencia.entidades.Categoria;
import persistencia.entidades.Menu;

/**
 *
 * @author ferney.medina
 */
public class Utilitaria {

    public static  String construirMenu(List<Menu> listMenus) {
        String imprime = "";
        for (Menu m : listMenus) {
            imprime += "<li class='dropdown" + ((m.getCodigoPadre() == 0) ? "" : (m.getListaMenu().size() > 0 ? "-submenu" : "")) + "'>"
                    + "<a " + (m.getListaMenu().size() == 0 ? "onclick=\"redirigir('" + m.getUrl() + "')\"" : "") + " class='dropdown-toggle' data-toggle='dropdown'"
                    + " role='button' aria-haspopup='true' aria-expanded='false' title=''>"
                    + m.getNombre();
            imprime += ((m.getCodigoPadre() == 0) ? "<span class='caret'></span>" : "");
            imprime += "</a>";

            if (m.getListaMenu().size() > 0) {
                imprime += "<ul class='dropdown-menu'>";
                imprime += construirMenu(m.getListaMenu());
                imprime += "</ul>";
            }
            imprime += "</li>";
        }
        return imprime;
    }

    public static String construirCategorias(List<Categoria> listCategoria) {
        String imprime = "";
        for (Categoria cat : listCategoria) {
            imprime += "<li class='liPersonalizada'><input type=\"radio\" "
                    + "name='categoria' class='categoria' "
                    + "class=\"checkPersonalizado\" value='" + cat.getCodigo()
                    + "'/><label>" + cat.getNombre() + "</label>";
            if (cat.getListaCategorias().size() > 0) {
                imprime += "<ul class='ulPersonalizada'>";
                imprime += construirCategorias(cat.getListaCategorias());
                imprime += "</ul>";
            }
            imprime += "</li>";
        }
        return imprime;
    }

    public static List<String> getPaginacion(int numRegistrosTabla, int cantPag) {
        double result = (double) numRegistrosTabla / (double) cantPag;
        int rangos = (int) result;
        int j = 1;
        ArrayList<String> list = new ArrayList<String>();
        int i = 1;
        String rang = 0 + "," + cantPag;
        list.add(rang);
        for (; i <= rangos; i++) {
            j = i * cantPag;
            rang = j + "," + cantPag;
            list.add(rang);
        }
        int res = numRegistrosTabla % cantPag;
        String nuevoValor = list.get(list.size() - 1);
        list.remove(nuevoValor);
        if (res != 0) {
            list.add(rangos * cantPag + "," + res);
        }
        return list;
    }

    public static String getSystemProperty(String referencia) {
        return System.getenv(referencia);
    }

    public static String createAlert(String title, String message, String tipo) {
        return "<div class=\"alert alert-" + tipo + " alert-dismissible\">\n"
                + "  <a href=\"#\" class=\"close\"  data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                + "  <strong>" + title + "!</strong> " + message + "\n"
                + "</div>";
    }
}
