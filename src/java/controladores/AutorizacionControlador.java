/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.AutorizacionFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.MultimediaFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Autorizacion;
import persistencia.entidades.Categoria;
import persistencia.entidades.Estructura;
import persistencia.entidades.Multimedia;
import persistencia.entidades.Prioridad;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import utilitarias.Visibilidad;

/**
 *
 * @author Jesus.Ramos
 */
@WebServlet(name = "AutorizacionControlador", urlPatterns = {"/AutorizacionControlador"})
public class AutorizacionControlador extends HttpServlet {

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
            if (request.getParameter("opc") != null) {
                int opcion = Integer.parseInt(request.getParameter("opc"));
                switch (opcion) {
                    case 1:
                        tablaRegistros(request, response);
                        break;
                    case 2:
                        recuperarAutorizacion(request,response);
                        break;
                    case 3:
                        crearRegistros(request, response);
                        break;
                    case 4:
                        editarRegistros(request, response);
                        break;
                    case 5:
                        borrarRegistros(request,response);
                        break;
                    case 6:
                        tablaRegistrosAdmin(request, response);
                        break;
                    case 7:
                        registrarSalida(request, response);
                        break;
                    case 8:
                        registrarEntrada(request, response);
                        break;
                }
            }
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (ParseException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(4));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }
    }

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
           /* AutorizacionFachada autoFachada = new AutorizacionFachada();
            GestionFachada estFach = new EstructuraFachada();
            Autorizacion autorizacion = new Autorizacion();
            List<Autorizacion> listArticulo = autoFachada.getListByPagination(autorizacion);
            String ref = "articuloEstadoAprobado";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estFach.getObject(estruc);
            String ref2 = "articuloEstadoInicial";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estFach.getObject(estruc2);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (user.getCodigo() == auto.getUsuario().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("titulo", auto.getTitulo());
                    jsonObj.put("nombreUsuario", auto.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", auto.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", auto.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", auto.getEstado().getNombre());
                    jsonObj.put("codigoEstado", auto.getEstado().getCodigo());
                    jsonObj.put("fechafinPublicacion", auto.getFechaFinPublicacion().toString());
                    if (auto.getEstado().getCodigo() == Integer.parseInt(estruc.getValor()) || auto.getEstado().getCodigo() == Integer.parseInt(estruc2.getValor())) {
                        jsonObj.put("fechaPublicacion", 0);
                    } else {
                        jsonObj.put("fechaPublicacion", auto.getFechaPublicacion().toString());
                    }
                    jsonArray.add(jsonObj);
                }
            }*/
            out.print(1);
        }
    }
    
    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
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
            String ref2 = "articuloEstadoInicial";
            Estructura estruc3 = new Estructura(ref2);
            estruc3 = (Estructura) estrucFachada.getObject(estruc3);
            art.setEstado(new ArticuloEstado(Integer.parseInt(estruc3.getValor())));
            art.setPrioridad(new Prioridad(Integer.parseInt(request.getParameter("prioridad"))));
            art.setPrecio(Double.parseDouble(request.getParameter("precio").replace(".","")));
            ArticuloFachada artFach = new ArticuloFachada();
            String ref = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            art.setFechaFinPublicacion(date);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            short visibilidad = Short.parseShort(request.getParameter("visibilidad"));
            if (request.getParameter("visibilidad") != null) {
                Visibilidad visibilidadArticulo = new Visibilidad(visibilidad);
                art.setVisibilidad(visibilidadArticulo);
            }
            
            if (codArt.equals("")) {
                artFach.insertObject(art);
            } else {
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }
            out.print(art.getCodigo());
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo el clasificado", "success"));
    }
    
    private void tablaRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setRango(request.getParameter("rango"));
            articulo.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByPagination(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            for (Articulo art : listArticulo) {
                if (art.getFechaPublicacion() == null && art.getEstado().getCodigo() == Integer.parseInt(estadoI)) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("codigo", art.getCodigo());
                    jsonObj.put("titulo", art.getTitulo());
                    jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", art.getEstado().getNombre());
                    jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
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
            estructura.setReferencia("tipoClasificado");
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
            obj.put("usuario_nombres", art.getUsuario().getNombres());
            obj.put("usuario_apellidos", art.getUsuario().getApellidos());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", ((art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion().toString())));
            obj.put("fecha_fin_publicacion", ((art.getFechaFinPublicacion() == null ? "" : art.getFechaFinPublicacion().toString())));
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("precio", art.getPrecio());
            obj.put("prioridad", art.getPrioridad().getValor());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            obj.put("visibilidad", art.getVisibilidad().getVisibilidad());
            if (art.getObservacionesAdmon() != null && !art.getObservacionesAdmon().isEmpty()) {
                obj.put("observacionAdmin", art.getObservacionesAdmon());
            } else {
                obj.put("observacionAdmin", "Sin observacion");
            }
            obj.put("Imagenes", jsArray);
            obj.put("Directorio", LecturaConfig.getValue("rutaVisualiza") + art.getCodigo() + "\\");
            out.print(obj);
        }
    }
    
    private void registrarEntrada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("cod")));
            art.setEstado(new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor())));
            ArticuloFachada artFach = new ArticuloFachada();
            artFach.updateObject(art);
            art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
            Utilitaria.enviarMailArticuloAprobado(art, art.getTitulo());
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se aprobo el clasificado", "success"));
    }
    
    private void registrarSalida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        PrintWriter out = response.getWriter();
        GestionFachada estructuraFachada = new EstructuraFachada();
        response.setContentType("text/html;charset=UTF-8");
        Articulo art = new Articulo(Integer.parseInt(request.getParameter("cod")));
        art.setObservacionesAdmon(request.getParameter("obs"));
        art.setEstado(new ArticuloEstado((Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("articuloDevuelto"))).getValor()))));
        ArticuloFachada artFach = new ArticuloFachada();
        artFach.updateObject(art);
        art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
        Utilitaria.enviarMailArticuloDevuelto(art, request.getParameter("obs"), request.getParameter("tit"));
        out.print(1);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se envio a correción el clasificado", "success"));
    }
    
    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            String ref = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.deleteObject(art);
            out.print(1);
            Utilitaria.borrarArchivos(LecturaConfig.getValue("rutaUpload") + art.getCodigo(), true);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se ha eliminado el clasificado correctamente", "success"));
    }
    private void recuperarAutorizacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            Articulo art = (Articulo) artFachada.getObject(new Articulo(Integer.parseInt(request.getParameter("id"))));
            UsuarioFachada userFachada = new UsuarioFachada();
            art.setUsuario((Usuario) userFachada.getObject(new Usuario(art.getUsuario().getCodigo())));
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Multimedia> listMult = multFachada.getListObject(art);
            JSONArray jsArray = new JSONArray();
            String pathOrigen = LecturaConfig.getValue("rutaVisualiza") + "\\" + art.getCodigo();
            for (Multimedia mult : listMult) {
                JSONObject obj1 = new JSONObject();
                obj1.put("ruta", pathOrigen + "\\" + mult.getCodigo() + "." + mult.getExtension());
                obj1.put("destacada", mult.getDestacada());
                jsArray.add(obj1);
            }
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("nombreUsuario", art.getUsuario().getUserName());
            if (art.getUsuario().getCelular() != null) {
                obj.put("telefono", art.getUsuario().getCelular());
            } else {
                obj.put("telefono", art.getUsuario().getTelefono());
            }
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fechaPublicacion", Utilitaria.convertirFecha(art.getFechaPublicacion()));
            obj.put("descripcion", art.getDescripcion());
            obj.put("precio", Utilitaria.conversionNatural(art.getPrecio()));
            obj.put("ubicacion", art.getComunidad().getDepartamentoCodigo().getNombre() + "," + art.getComunidad().getCiudadCodigo().getNombre() + "," + art.getComunidad().getNombre());
            obj.put("imagenes", jsArray);
            out.print(obj);
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
