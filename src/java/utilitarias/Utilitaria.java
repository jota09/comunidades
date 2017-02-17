/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import persistencia.entidades.Articulo;
import persistencia.entidades.Categoria;
import persistencia.entidades.Menu;

/**
 *
 * @author ferney.medina
 */
public class Utilitaria {

    public static String construirMenu(List<Menu> listMenus) {
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
            imprime += "<option value='" + cat.getCodigo() + "'>"
                    + cat.getNombre() + "</option>";
            if (cat.getListaCategorias().size() > 0) {
                imprime += construirCategorias(cat.getListaCategorias());
            }
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

    public static String convertirFecha(Date fecha) {
        String[] fechaFraccionada = fecha.toString().split("-");
        if (fechaFraccionada[1].equals("01")) {
            fechaFraccionada[1] = "Enero";
        }
        if (fechaFraccionada[1].equals("02")) {
            fechaFraccionada[1] = "Febrero";
        }
        if (fechaFraccionada[1].equals("03")) {
            fechaFraccionada[1] = "Marzo";
        }
        if (fechaFraccionada[1].equals("04")) {
            fechaFraccionada[1] = "Abril";
        }
        if (fechaFraccionada[1].equals("05")) {
            fechaFraccionada[1] = "Mayo";
        }
        if (fechaFraccionada[1].equals("06")) {
            fechaFraccionada[1] = "Junio";
        }
        if (fechaFraccionada[1].equals("07")) {
            fechaFraccionada[1] = "Julio";
        }
        if (fechaFraccionada[1].equals("08")) {
            fechaFraccionada[1] = "Agosto";
        }
        if (fechaFraccionada[1].equals("09")) {
            fechaFraccionada[1] = "Septiembre";
        }
        if (fechaFraccionada[1].equals("10")) {
            fechaFraccionada[1] = "Octubre";
        }
        if (fechaFraccionada[1].equals("11")) {
            fechaFraccionada[1] = "Noviembre";
        }
        if (fechaFraccionada[1].equals("12")) {
            fechaFraccionada[1] = "Diciembre";
        }
        return fechaFraccionada[2] + " " + fechaFraccionada[1] + " de " + fechaFraccionada[0];

    }

    public static String conversionNatural(double valor) {
        DecimalFormat num = new DecimalFormat("#,###");
        return num.format(valor);
    }
    
    public static String filtros(String busqueda, String tipoDato, String campo, String naturaleza, String valor, String valor2, String condicion){
        if(naturaleza.equals("like")){
            busqueda += campo+" like '" + valor + "%',";
        }
        if(naturaleza.equals("where")){
            if(tipoDato.equals("int") || tipoDato.equals("date")){
                if(condicion.equals("igual")){
                    busqueda += campo+" = "+valor+",";
                }
                if(condicion.equals("mayor")){
                    busqueda += campo+" > "+valor+",";
                }
                if(condicion.equals("menor")){
                    busqueda += campo+" < "+valor+",";                    
                }
                if(condicion.equals("mayorIgual")){
                    busqueda += campo+" >= "+valor+",";
                }
                if(condicion.equals("menorIgual")){
                    busqueda += campo+" <= "+valor+",";                    
                }
                if(condicion.equals("rango")){
                    busqueda += campo+" between "+valor+" and "+valor2+",";
                }
            }
            if(tipoDato.equals("char")){
                busqueda += campo+" = '"+valor+"'";
            }
        }
        if(naturaleza.equals("order")){
            busqueda += "/ORDER BY " + campo + " "+valor;
        }
        return busqueda;
    }
    
    public static String filtros(String busqueda, String tipoDato, String campo, String naturaleza, String valor, String valor2, String condicion){
        if(naturaleza.equals("like")){
            busqueda += campo+" like '" + valor + "%',";
        }
        if(naturaleza.equals("where")){
            if(tipoDato.equals("int") || tipoDato.equals("date")){
                if(condicion.equals("igual")){
                    busqueda += campo+" = "+valor+",";
                }
                if(condicion.equals("mayor")){
                    busqueda += campo+" > "+valor+",";
                }
                if(condicion.equals("menor")){
                    busqueda += campo+" < "+valor+",";                    
                }
                if(condicion.equals("mayorIgual")){
                    busqueda += campo+" >= "+valor+",";
                }
                if(condicion.equals("menorIgual")){
                    busqueda += campo+" <= "+valor+",";                    
                }
                if(condicion.equals("rango")){
                    busqueda += campo+" between "+valor+" and "+valor2+",";
                }
            }
            if(tipoDato.equals("char")){
                busqueda += campo+" = '"+valor+"'";
            }
        }
        if(naturaleza.equals("order")){
            busqueda += "/ORDER BY " + campo + " "+valor;
        }
        return busqueda;
    }
}
