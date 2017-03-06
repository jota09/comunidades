/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import fachada.ErrorFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.OpcionesFiltroFachada;
import java.io.File;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import persistencia.entidades.Articulo;
import persistencia.entidades.Categoria;
import persistencia.entidades.Menu;
import persistencia.entidades.Error;
import persistencia.entidades.Estructura;
import persistencia.entidades.OpcionesFiltro;

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

    public static String filtros(String busqueda, String tipoDato, String campo, String naturaleza, String valor, String valor2, String condicion) {
        if (naturaleza.equals("like")) {
            busqueda += campo + " like '" + valor + "%',";
        }
        if (naturaleza.equals("where")) {
            if (tipoDato.equals("int") || tipoDato.equals("date")) {
                if (condicion.equals("igual")) {
                    busqueda += campo + " = " + valor + ",";
                }
                if (condicion.equals("mayor")) {
                    busqueda += campo + " > " + valor + ",";
                }
                if (condicion.equals("menor")) {
                    busqueda += campo + " < " + valor + ",";
                }
                if (condicion.equals("mayorIgual")) {
                    busqueda += campo + " >= " + valor + ",";
                }
                if (condicion.equals("menorIgual")) {
                    busqueda += campo + " <= " + valor + ",";
                }
                if (condicion.equals("rango")) {
                    busqueda += campo + " between " + valor + " and " + valor2 + ",";
                }
            }
            if (tipoDato.equals("char")) {
                busqueda += campo + " = '" + valor + "'";
            }
        }
        if (naturaleza.equals("order")) {
            busqueda += "/ORDER BY " + campo + " " + valor;
        }
        return busqueda;
    }

    public static void escribeError(Error error) {
        Calendar calendar = Calendar.getInstance();
        GestionFachada errorFachada = new ErrorFachada();
        GestionFachada estructuraFachada = new EstructuraFachada();
        errorFachada.insertObject(error);
        String fecha = calendar.getTime().toString();
        String mensaje = "Fecha:" + fecha + " Tipo_Error:" + error.getTipoError().getCodigo()
                + " Descripcion:" + error.getDescripcion() + " Clase:" + error.getClase() + " Metodo:" + error.getMetodo();
        String host = ((Estructura) estructuraFachada.getObject(new Estructura("hostServerSMTP"))).getValor();
        int puerto = (Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("puertoSMTP"))).getValor()));
        String correo = ((Estructura) estructuraFachada.getObject(new Estructura("correoSoporte"))).getValor();
        String usuario = ((Estructura) estructuraFachada.getObject(new Estructura("usuarioMailSoporte"))).getValor();
        String password = ((Estructura) estructuraFachada.getObject(new Estructura("passCorreoSoporte"))).getValor();
        String serverSSL = ((Estructura) estructuraFachada.getObject(new Estructura("sslSMTP"))).getValor();
        String autenticacion = ((Estructura) estructuraFachada.getObject(new Estructura("autenticacionSMTP"))).getValor();
        String starttls = ((Estructura) estructuraFachada.getObject(new Estructura("tlsSMTP"))).getValor();
        ServicioDeEnvioMail envioMail = new ServicioDeEnvioMail(host, puerto, correo, usuario, password, starttls, autenticacion, serverSSL);
        envioMail.sendEmail(mensaje, "Error " + fecha, correo);
    }

    public static void enviarMailArticuloDevuelto(Object obj, String obs, String tit) {
        Articulo art = (Articulo) obj;
        GestionFachada estructuraFachada = new EstructuraFachada();
        String titulo = ((Estructura) estructuraFachada.getObject(new Estructura("tituloAdminDevolucion"))).getValor();
        String cuerpo = ((Estructura) estructuraFachada.getObject(new Estructura("cuerpoAdminDevolucion"))).getValor();
        String firma = ((Estructura) estructuraFachada.getObject(new Estructura("firmaAdminDevolucion"))).getValor();
        String mensaje = titulo + " " + art.getUsuario().getNombres() + " " + art.getUsuario().getApellidos() + " " + "\n\n\n" + cuerpo + "\n" + obs
                + "\n\n\n" + firma;
        String host = ((Estructura) estructuraFachada.getObject(new Estructura("hostServerSMTP"))).getValor();
        int puerto = (Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("puertoSMTP"))).getValor()));
        String correo = ((Estructura) estructuraFachada.getObject(new Estructura("correoSoporte"))).getValor();
        String usuario = ((Estructura) estructuraFachada.getObject(new Estructura("usuarioMailSoporte"))).getValor();
        String password = ((Estructura) estructuraFachada.getObject(new Estructura("passCorreoSoporte"))).getValor();
        String serverSSL = ((Estructura) estructuraFachada.getObject(new Estructura("sslSMTP"))).getValor();
        String autenticacion = ((Estructura) estructuraFachada.getObject(new Estructura("autenticacionSMTP"))).getValor();
        String starttls = ((Estructura) estructuraFachada.getObject(new Estructura("tlsSMTP"))).getValor();
        ServicioDeEnvioMail envioMail = new ServicioDeEnvioMail(host, puerto, correo, usuario, password, starttls, autenticacion, serverSSL);
        envioMail.sendEmail(mensaje, "Correciones articulo " + tit, art.getUsuario().getCorreo());
    }

    public static void enviarMailArticuloEliminado(Object obj, String obs, String tit) {
        Articulo art = (Articulo) obj;
        GestionFachada estructuraFachada = new EstructuraFachada();
        String titulo = ((Estructura) estructuraFachada.getObject(new Estructura("tituloAdminEliminacion"))).getValor();
        String cuerpo = ((Estructura) estructuraFachada.getObject(new Estructura("cuerpoAdminEliminacion"))).getValor();
        String firma = ((Estructura) estructuraFachada.getObject(new Estructura("firmaAdminEliminacion"))).getValor();
        String mensaje = titulo + " " + art.getUsuario().getNombres() + " " + art.getUsuario().getApellidos() + " " + "\n\n\n" + cuerpo + "\n" + obs + "\n\n\n" + firma;
        String host = ((Estructura) estructuraFachada.getObject(new Estructura("hostServerSMTP"))).getValor();
        int puerto = (Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("puertoSMTP"))).getValor()));
        String correo = ((Estructura) estructuraFachada.getObject(new Estructura("correoSoporte"))).getValor();
        String usuario = ((Estructura) estructuraFachada.getObject(new Estructura("usuarioMailSoporte"))).getValor();
        String password = ((Estructura) estructuraFachada.getObject(new Estructura("passCorreoSoporte"))).getValor();
        String serverSSL = ((Estructura) estructuraFachada.getObject(new Estructura("sslSMTP"))).getValor();
        String autenticacion = ((Estructura) estructuraFachada.getObject(new Estructura("autenticacionSMTP"))).getValor();
        String starttls = ((Estructura) estructuraFachada.getObject(new Estructura("tlsSMTP"))).getValor();
        ServicioDeEnvioMail envioMail = new ServicioDeEnvioMail(host, puerto, correo, usuario, password, starttls, autenticacion, serverSSL);
        envioMail.sendEmail(mensaje, "Eliminación articulo " + tit, art.getUsuario().getCorreo());
    }

    public static void enviarMailArticuloAprobado(Object obj, String tit) {
        Articulo art = (Articulo) obj;
        Calendar calendar = Calendar.getInstance();
        String fecha = calendar.getTime().toString();
        GestionFachada estructuraFachada = new EstructuraFachada();
        String titulo = ((Estructura) estructuraFachada.getObject(new Estructura("tituloAdminAprobacion"))).getValor();
        String cuerpo = ((Estructura) estructuraFachada.getObject(new Estructura("cuerpoAdminAprobacion"))).getValor();
        String firma = ((Estructura) estructuraFachada.getObject(new Estructura("firmaAdminAprobacion"))).getValor();
        String mensaje = titulo + " " + art.getUsuario().getNombres() + " " + art.getUsuario().getApellidos() + " " + "\n"
                + "\n" + cuerpo + "\n" + tit + "\nPublicado " + fecha + "\n\n\n" + firma;
        String host = ((Estructura) estructuraFachada.getObject(new Estructura("hostServerSMTP"))).getValor();
        int puerto = (Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("puertoSMTP"))).getValor()));
        String correo = ((Estructura) estructuraFachada.getObject(new Estructura("correoSoporte"))).getValor();
        String usuario = ((Estructura) estructuraFachada.getObject(new Estructura("usuarioMailSoporte"))).getValor();
        String password = ((Estructura) estructuraFachada.getObject(new Estructura("passCorreoSoporte"))).getValor();
        String serverSSL = ((Estructura) estructuraFachada.getObject(new Estructura("sslSMTP"))).getValor();
        String autenticacion = ((Estructura) estructuraFachada.getObject(new Estructura("autenticacionSMTP"))).getValor();
        String starttls = ((Estructura) estructuraFachada.getObject(new Estructura("tlsSMTP"))).getValor();
        ServicioDeEnvioMail envioMail = new ServicioDeEnvioMail(host, puerto, correo, usuario, password, starttls, autenticacion, serverSSL);
        envioMail.sendEmail(mensaje, "Aprobación articulo " + tit, art.getUsuario().getCorreo());
    }

    public static String construyeCondicion(String jsonArrayCondiciones) throws ParseException {
        JSONParser parser = new JSONParser();
        String condicion = "";
        GestionFachada opcionesFachada = new OpcionesFiltroFachada();
        JSONArray array = (JSONArray) parser.parse(jsonArrayCondiciones);
        for (int i = 0; i < array.size(); i++) {
            OpcionesFiltro opcion = new OpcionesFiltro(Integer.parseInt(array.get(i).toString()));
            opcionesFachada.getObject(opcion);
            condicion += opcion.getFiltro().getCampo() + " " + opcion.getFiltro().getCondicionFiltro().getCondicion().replace("?", opcion.getValor()) + ((i + 1 < array.size()) ? " and " : "");
        }
        return condicion;
    }

    public static void borrarArchivos(String path, boolean borrarDir) {        
        File file = new File(path);
        File[] files = file.listFiles();
        if (files.length > 0) {
            for (File f : files) {
                f.delete();
            }
        }
        if (borrarDir) {
            file.delete();
        }
    }

    public static String genCodigoRegComunidad(int codigoComunidad) {
        String codigoGen = String.valueOf(codigoComunidad);
        for (int i = 0; i < 12; i++) {
            int ascii = (int) (Math.random() * (48 - (122 + 1)) + (122));
            codigoGen += (char) ascii;
        }
        return codigoGen;
    }

}
