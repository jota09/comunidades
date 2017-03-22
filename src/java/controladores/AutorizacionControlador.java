/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.AutorizacionFachada;
import fachada.EstructuraFachada;
import fachada.MotivoAutorizacionFachada;
import fachada.MultimediaAutorizacionFachada;
import fachada.UsuarioFachada;
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
import org.json.simple.JSONObject;
import persistencia.entidades.Articulo;
import persistencia.entidades.Autorizacion;
import persistencia.entidades.EstadoAutorizacion;
import persistencia.entidades.Estructura;
import persistencia.entidades.MotivoAutorizacion;
import persistencia.entidades.Multimedia;
import persistencia.entidades.MultimediaAutorizacion;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

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
                System.out.println(opcion);
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
                    case 10:
                        buscarRegistros(request, response);
                        break;
                    case 11:
                        registrarAprobado(request, response);
                        break;
                    case 12:
                        buscarRegistrosAdmin(request, response);
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
                if (((Usuario) request.getSession().getAttribute("user")).getCodigo() == auto.getUsuarioCodigo().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                    jsonObj.put("documento_ingreso", auto.getDocumentoPersonaIngresa());
                    jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                    jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                    jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                    jsonObj.put("codigo_estado", auto.getEstado().getCodigo());
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
            auto.setEmpresaContratista(request.getParameter("empresa"));
            auto.setDocumentoPersonaIngresa(request.getParameter("documento"));
            auto.setDescripcion(request.getParameter("descr"));
            auto.setEstado(new EstadoAutorizacion(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("autorizacionEstadoInicial"))).getValor())));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("fechaAuto"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            auto.setFechaautorizacion(date);
            auto.setMotivo(new MotivoAutorizacion(Integer.parseInt(request.getParameter("motivo"))));
            auto.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            if (codigo.equals("")) {
                autoFachada.insertObject(auto);
            } else {
                auto.setCodigo(Integer.parseInt(codigo));
                autoFachada.updateObject(auto);
            }
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo el clasificado", "success"));
    }

    private void tablaRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estruFach = new EstructuraFachada();
            Estructura estruc = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoInicial")));
            Estructura estruc2 = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoFinaliza")));
            Estructura estruc3 = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoVencida")));
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setRango(request.getParameter("rango"));
            autorizacion.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<Autorizacion> listArticulo = autoFachada.getListByPagination(autorizacion);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (Integer.parseInt(estruc.getValor()) != auto.getEstado().getCodigo() && Integer.parseInt(estruc2.getValor()) != auto.getEstado().getCodigo() && Integer.parseInt(estruc3.getValor()) != auto.getEstado().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                    jsonObj.put("documento_ingreso", auto.getDocumentoPersonaIngresa());
                    jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                    jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                    jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                    jsonObj.put("codigo_estado", auto.getEstado().getCodigo());
                    jsonObj.put("estado", auto.getEstado().getNombre());
                    jsonObj.put("motivo", auto.getMotivo().getNombre());
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void editarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Autorizacion auto = new Autorizacion(Integer.parseInt(request.getParameter("cod")));
            AutorizacionFachada autoFach = new AutorizacionFachada();
            auto = (Autorizacion) autoFach.getObject(auto);
            MultimediaAutorizacionFachada multFachada = new MultimediaAutorizacionFachada();
            MultimediaAutorizacion multi = (MultimediaAutorizacion) multFachada.getObject(auto);
            String pathOrigen = LecturaConfig.getValue("rutaVisualizaAutorizacion") + "\\" + auto.getCodigo();
            JSONObject obj = new JSONObject();
            obj.put("codigo", auto.getCodigo());
            obj.put("nombreInv", auto.getPersonaIngresa());
            obj.put("documento", auto.getDocumentoPersonaIngresa());
            obj.put("empresaIngre", (auto.getEmpresaContratista() != null ? auto.getEmpresaContratista() : ""));
            obj.put("fechaAuto", auto.getFechaautorizacion().toString());
            obj.put("descripcion", (auto.getDescripcion() != null ? auto.getDescripcion() : "false"));
            obj.put("motivo", auto.getMotivo().getCodigo());
            if (multi != null && multi.getCodigo() != 0) {
                obj.put("ruta", pathOrigen + "\\" + multi.getCodigo() + "." + multi.getExtension());
            } else {
                obj.put("ruta", null);
            }
            obj.put("nombre_motivo", auto.getMotivo().getNombre());

            out.print(obj);
        }
    }

    private void registrarEntrada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Autorizacion auto = new Autorizacion(Integer.parseInt(request.getParameter("cod")));
            auto.setEstado(new EstadoAutorizacion(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("autorizacionEstadoEspera"))).getValor())));
            AutorizacionFachada autoFach = new AutorizacionFachada();
            autoFach.updateObject(auto);
            out.print(request.getParameter("cod"));
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se registro la entrada", "success"));
    }
    
    private void registrarAprobado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Autorizacion auto = new Autorizacion(Integer.parseInt(request.getParameter("cod")));
            auto.setEstado(new EstadoAutorizacion(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("autorizacionEstadoAprobado"))).getValor())));
            AutorizacionFachada autoFach = new AutorizacionFachada();
            autoFach.updateObject(auto);
            out.print(request.getParameter("cod"));
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se registro la entrada", "success"));
    }

    private void registrarSalida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Autorizacion auto = new Autorizacion(Integer.parseInt(request.getParameter("cod")));
            auto.setEstado(new EstadoAutorizacion(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("autorizacionEstadoFinaliza"))).getValor())));
            AutorizacionFachada autoFach = new AutorizacionFachada();
            autoFach.updateObject(auto);
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se registro la salida", "success"));
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Autorizacion auto = new Autorizacion(Integer.parseInt(request.getParameter("cod").trim()));
            AutorizacionFachada autoFacha = new AutorizacionFachada();
            MultimediaAutorizacionFachada multiFacha = new MultimediaAutorizacionFachada();
            multiFacha.deleteObject(new MultimediaAutorizacion(auto));
            autoFacha.deleteObject(auto);
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se ha eliminado la autorizacion", "success"));
    }

    private void recuperarAutorizacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            Articulo art = (Articulo) artFachada.getObject(new Articulo(Integer.parseInt(request.getParameter("id"))));
            UsuarioFachada userFachada = new UsuarioFachada();
            art.setUsuario((Usuario) userFachada.getObject(new Usuario(art.getUsuario().getCodigo())));
            MultimediaAutorizacionFachada multFachada = new MultimediaAutorizacionFachada();
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

    private void buscarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setRango(request.getParameter("rango"));
            autorizacion.setUsuarioCodigo((Usuario) request.getSession().getAttribute("user"));
            autorizacion.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            autorizacion.setBusqueda(request.getParameter("buscar"));
            List<Autorizacion> listArticulo = autoFachada.getListByCondition(autorizacion);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (((Usuario) request.getSession().getAttribute("user")).getCodigo() == auto.getUsuarioCodigo().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                    jsonObj.put("documento_ingreso", auto.getDocumentoPersonaIngresa());
                    jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                    jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                    jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                    jsonObj.put("codigo_estado", auto.getEstado().getCodigo());
                    jsonObj.put("estado", auto.getEstado().getNombre());
                    jsonObj.put("motivo", auto.getMotivo().getNombre());
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }
    
    private void buscarRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            AutorizacionFachada autoFachada = new AutorizacionFachada();
            Autorizacion autorizacion = new Autorizacion();
            EstructuraFachada estruFach = new EstructuraFachada();
            Estructura estruc = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoInicial")));
            Estructura estruc2 = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoFinaliza")));
            Estructura estruc3 = ((Estructura) estruFach.getObject(new Estructura("autorizacionEstadoVencida")));
            autorizacion.setRango(request.getParameter("rango"));
            autorizacion.setComunidadcodigo(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            autorizacion.setBusqueda(request.getParameter("buscar"));
            List<Autorizacion> listArticulo = autoFachada.getListByCondition(autorizacion);
            JSONArray jsonArray = new JSONArray();
            for (Autorizacion auto : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (Integer.parseInt(estruc.getValor()) != auto.getEstado().getCodigo() && Integer.parseInt(estruc2.getValor()) != auto.getEstado().getCodigo() && Integer.parseInt(estruc3.getValor()) != auto.getEstado().getCodigo()) {
                    jsonObj.put("codigo", auto.getCodigo());
                    jsonObj.put("persona_ingresa", auto.getPersonaIngresa());
                    jsonObj.put("documento_ingreso", auto.getDocumentoPersonaIngresa());
                    jsonObj.put("fecha_autorizacion", auto.getFechaautorizacion().toString());
                    jsonObj.put("autoriza", auto.getUsuarioCodigo().getNombres() + " " + auto.getUsuarioCodigo().getApellidos());
                    jsonObj.put("direccion", auto.getUsuarioCodigo().getInmueble().getUbicacion());
                    jsonObj.put("codigo_estado", auto.getEstado().getCodigo());
                    jsonObj.put("estado", auto.getEstado().getNombre());
                    jsonObj.put("motivo", auto.getMotivo().getNombre());
                    jsonArray.add(jsonObj);
                }
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
