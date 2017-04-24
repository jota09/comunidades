/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
import fachada.CondicionPaginacionFachada;
import fachada.GestionFachada;
import fachada.PerfilFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Perfil;
import persistencia.entidades.TipoError;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "GestionPerfilControlador", urlPatterns = {"/GestionPerfilControlador"})
public class GestionPerfilControlador extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.setContentType("text/html;charset=UTF-8");
            int op = Integer.parseInt(request.getParameter("op"));
            switch (op) {
                case 1: {
                    cargarComunidad(request, response);
                    break;
                }
                case 2: {
                    recuperarPerfil(request, response);
                    break;
                }
                case 3: {
                    editarPerfil(request, response);
                    break;
                }

                case 4: {
                    guardarPerfil(request, response);
                    break;
                }
                case 5: {
                    eliminarPerfil(request, response);
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

    private void cargarComunidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada comunidadFachada = new ComunidadFachada();
        List<Comunidad> comunidades = comunidadFachada.getListObject();
        JSONArray array = new JSONArray();
        for (Comunidad comunidad : comunidades) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", comunidad.getCodigo());
            obj.put("nombre", comunidad.getNombre());
            array.add(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(array);
    }

    private void recuperarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rango = request.getParameter("rango");
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda = request.getParameter("busqueda");
        GestionFachada condicionFachada = new CondicionPaginacionFachada();
        CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
        condicionFachada.getObject(condicionPaginacion);
        CondicionPaginado condicion = new CondicionPaginado();
        condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
        GestionFachada perfilFachada = new PerfilFachada();
        List<Perfil> perfiles = perfilFachada.getListByPagination(condicion);
        JSONArray array = new JSONArray();
        for (Perfil perfil : perfiles) {
            JSONObject obj = new JSONObject();
            obj.put("codPerfil", perfil.getCodigo());
            obj.put("nombrePerfil", perfil.getNombre());
            obj.put("codComunidad", perfil.getComunidad().getCodigo());
            obj.put("nombreComunidad", perfil.getComunidad().getNombre());
            array.add(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(array);

    }

    private void editarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada perfilFachada = new PerfilFachada();
        Perfil perfil = new Perfil(Integer.parseInt(request.getParameter("cod")));
        perfilFachada.getObject(perfil);
        JSONObject obj = new JSONObject();
        obj.put("perfilNombre", perfil.getNombre());
        obj.put("comunidadNombre", perfil.getComunidad().getNombre());
        PrintWriter out = response.getWriter();
        out.print(obj);
    }

    private void guardarPerfil(HttpServletRequest request, HttpServletResponse response) {
        String codigoPerfil = request.getParameter("cod");
        HttpSession sesion = request.getSession();
        Perfil perfil = new Perfil();
        perfil.setNombre(request.getParameter("nombrePerfil").toUpperCase().trim());
        GestionFachada perfilFachada = new PerfilFachada();
        if (codigoPerfil.isEmpty()) {
            Comunidad comunidad = new Comunidad(Integer.parseInt(request.getParameter("comunidad")));
            perfil.setComunidad(comunidad);
            perfilFachada.insertObject(perfil);
            sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se Creo el nuevo perfil Satisfactoriamente", "success"));
        } else {
            perfil.setCodigo(Integer.parseInt(codigoPerfil));
            perfilFachada.updateObject(perfil);
            sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se Actualizo Correctamente el Perfil", "success"));
        }

    }

    private void eliminarPerfil(HttpServletRequest request, HttpServletResponse response) {
        Perfil perfil=new Perfil(Integer.parseInt(request.getParameter("cod")));
        GestionFachada perfilFachada = new PerfilFachada();
        HttpSession sesion = request.getSession();
        perfilFachada.deleteObject(perfil);
        sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se Elimino el Perfil", "success"));
    }

}
