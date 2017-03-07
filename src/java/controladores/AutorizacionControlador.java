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
import fachada.MotivoAutorizacionFachada;
import fachada.MultimediaFachada;
import fachada.PrioridadFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import persistencia.entidades.EstadoAutorizacion;
import persistencia.entidades.Estructura;
import persistencia.entidades.MotivoAutorizacion;
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
                        recuperarAutorizacion(request, response);
                        break;
                    case 3:
                        crearRegistros(request, response);
                        break;
                    case 4:
                        editarRegistros(request, response);
                        break;
                    case 5:
                        borrarRegistros(request, response);
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
                    case 9:
                        recuperarMotivo(request, response);
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

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setRango(request.getParameter("rango"));
            autorizacion.setUsuarioCodigo((Usuario) request.getSession().getAttribute("user"));
            autorizacion.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<Autorizacion> listArticulo = autoFachada.getListByPagination(autorizacion);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (((Usuario) request.getSession().getAttribute("user")).getCodigo() == auto.getComunidadcodigo().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                    jsonObj.put("documento_ingres", auto.getDocumentoPersonaIngresa());
                    jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                    jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                    jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                    jsonObj.put("estado", auto.getEstado().getNombre());
                    jsonObj.put("motivo", auto.getMotivo().getNombre());
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            String codigo = request.getParameter("codigo");
            Autorizacion auto = new Autorizacion();
            auto.setUsuarioCodigo(((Usuario) request.getSession().getAttribute("user")));
            auto.setPersonaIngresa(request.getParameter("nombre"));
            auto.setDocumentoPersonaIngresa(request.getParameter("documento"));
            auto.setEstado(new EstadoAutorizacion(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("autorizacionEstadoInicial"))).getValor())));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("fechaAuto"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            auto.setFechaautorizacion(date);
            auto.setMotivo(new MotivoAutorizacion(Integer.parseInt(request.getParameter("motivo"))));
            auto.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            System.out.println("Obj autorizacion: "+auto);
            if (codigo.equals("")) {
                System.out.println("Entro aqui en insert");
                autoFachada.insertObject(auto);
            } else {
                System.out.println("Entro aqui en update");
                auto.setCodigo(Integer.parseInt(codigo));
                autoFachada.updateObject(auto);
            }
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo el clasificado", "success"));
    }

    private void tablaRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setRango(request.getParameter("rango"));
            autorizacion.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<Autorizacion> listArticulo = autoFachada.getListByPagination(autorizacion);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", auto.getCodigo());
                jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                jsonObj.put("documento_ingres", auto.getDocumentoPersonaIngresa());
                jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                jsonObj.put("estado", auto.getEstado().getNombre());
                jsonObj.put("motivo", auto.getMotivo().getNombre());
                jsonArray.add(jsonObj);
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

    private void recuperarMotivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            MotivoAutorizacionFachada motFachada = new MotivoAutorizacionFachada();
            List<MotivoAutorizacion> listMotivo = motFachada.getListObject();
            JSONArray array = new JSONArray();
            for (MotivoAutorizacion mot : listMotivo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", mot.getCodigo());
                obj.put("nombre", mot.getNombre());
                array.add(obj);
            }
            out.print(array);
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
