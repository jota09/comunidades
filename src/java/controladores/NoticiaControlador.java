/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.MultimediaFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import org.json.simple.JSONObject;
import persistencia.entidades.Estructura;
import persistencia.entidades.Multimedia;
import persistencia.entidades.Prioridad;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
//Prueba de que ya sincronizo el proyecto con alejandro
//Prueba de que ya sincronizo el proyecto con manuel
//Verificacion....

/**
 *
 * @author ferney.medina
 */
@WebServlet(name = "Noticia", urlPatterns = {"/NoticiaControlador"})
public class NoticiaControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            if (request.getParameter("op") != null) {
                int opcion = Integer.parseInt(request.getParameter("op"));
                switch (opcion) {
                    case 1:
                        tablaRegistros(request, response);
                        break;
                    case 2:
                        crearRegistros(request, response);
                        break;
                    case 3:
                        editarRegistros(request, response);
                        break;
                    case 4:
                        borrarRegistros(request, response);
                        break;
                    case 5:
                        aprobarRegistros(request, response);
                        break;
                    case 6:
                        cargarCategorias(request, response);
                        break;
                    case 7:
                        filtrarCategorias(request, response);
                        break;
                    case 8:
                        buscarRegistros(request, response);
                        break;
                    case 9:
                        tipoArticulo(request, response);
                        break;
                    default:break;
                }
            } else {
                System.out.println("La opcion es nula por favor verificar");
            }
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (ParseException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(4));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }
    }

    private void tipoArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoNoticia");
            Estructura est = (Estructura) estFach.getObject(estructura);
            out.print(est.getValor());
        }
    }

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();            
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setRango(request.getParameter("rango"));
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByPagination(articulo);
            String ref = "articuloEstadoAprobado";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estFach.getObject(estruc);
            String ref2 = "articuloEstadoInicial";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estFach.getObject(estruc2);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getEstado().getNombre());
                jsonObj.put("codigoEstado", art.getEstado().getCodigo());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                if (art.getEstado().getCodigo() == Integer.parseInt(estruc.getValor()) || art.getEstado().getCodigo() == Integer.parseInt(estruc2.getValor())) {
                    jsonObj.put("fechaPublicacion", 0);
                } else {
                    jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                }
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            String codArt = request.getParameter("codArt");
            Articulo art = new Articulo();
            art.setTitulo(request.getParameter("titulo"));
            art.setDescripcion(request.getParameter("descripcion"));
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            int cat;
            cat = Integer.parseInt(request.getParameter("categoria"));
            ArticuloEstado artEstado = new ArticuloEstado();
            artEstado.setCodigo(1);
            art.setEstado(artEstado);
            art.setPrioridad(new Prioridad(1));//La noticia no tiene prioridad pero sin embargo se le envia el 1(baja)
            ArticuloFachada artFach = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            tpArt.setCodigo(2);//Tipo articulo noticia es 2
            art.setTipoArticulo(tpArt);
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            art.setFechaFinPublicacion(date);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            art.setVisibilidad(Short.parseShort(request.getParameter("visibilidad")));
            if (codArt.equals("")) {
                artFach.insertObject(art);
            } else {
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }
            out.print(art.getCodigo());
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo la noticia", "success"));
    }

    private void editarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod"));
            Articulo art = new Articulo();
            art.setCodigo(cod);
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoNoticia");
            Estructura est = (Estructura) estFach.getObject(estructura);
            tpArt.setCodigo(Integer.parseInt(est.getValor()));
            art.setTipoArticulo(tpArt);
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            art = (Articulo) artFachada.getObject(art);
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Multimedia> listMult = multFachada.getListObject(art);
            JSONArray jsArray = new JSONArray();
            for (Multimedia mult : listMult) {
                JSONObject obj1 = new JSONObject();
                String name = String.valueOf(mult.getCodigo());
                obj1.put("name", name);
                obj1.put("ext", mult.getExtension());
                obj1.put("destacada", mult.getDestacada());
                jsArray.add(obj1);
            }
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("usuario_codigo", art.getUsuario().getCodigo());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", ((art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion().toString())));
            obj.put("fecha_fin_publicacion", ((art.getFechaFinPublicacion() == null ? "" : art.getFechaFinPublicacion().toString())));
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            obj.put("visibilidad", art.getVisibilidad());
            obj.put("Imagenes", jsArray);
            obj.put("Directorio", LecturaConfig.getValue("rutaVisualiza") + art.getCodigo() + "/");
            out.print(obj);
        }
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            TipoArticulo tpArt = new TipoArticulo();
            tpArt.setCodigo(2);//Tipo articulo noticia es 2
            art.setTipoArticulo(tpArt);
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.deleteObject(art);
        }
    }

    private void aprobarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

        }
    }

    private void cargarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada = new CategoriaFachada();
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));
        }
    }

    private void filtrarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            //articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            articulo.setBusqueda("%");
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getCategoria().getNombre());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    private void buscarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getCategoria().getNombre());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
