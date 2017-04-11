/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.AtributoFachada;
import fachada.CondicionPaginacionFachada;
import fachada.GestionFachada;
import fachada.VistaAtributoFachada;
import fachada.VistaFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Atributo;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.TipoError;
import persistencia.entidades.Vista;
import persistencia.entidades.VistaAtributo;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import utilitarias.CondicionPaginado;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "AtributoControlador", urlPatterns = {"/AtributoControlador"})
public class AtributoControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            int op = Integer.parseInt(request.getParameter("op"));
            switch (op) {
                case 1: {
                    getAtributos(request, response);
                    break;
                }
                case 2: {
                    getVistaAtributo(request, response);
                    break;
                }
                case 3: {
                    getVistas(request, response);
                    break;
                }
                case 4: {
                    insertarAtributo(request, response);
                    break;
                }
                case 5: {
                    eliminarAtributo(request, response);
                    break;
                }
                case 6: {
                    asociarAtributoVista(request, response);
                    break;
                }
                case 7: {
                    elimiarVistaAtributo(request, response);
                    break;
                }
                case 8: {
                    getAtributosConCondicion(request, response);
                    break;
                }
                case 9: {
                    getAtributoVistaConCondicion(request, response);
                    break;
                }
                case 10: {
                    getAtributoConPaginacion(request, response);
                    break;
                }
                case 11: {
                    getVistaAtributoConPaginacion(request, response);
                    break;
                }
            }
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }

    }

    public void getVistaAtributoConPaginacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String rango = request.getParameter("rango");
            String condicionesPag = request.getParameter("condicionesPag");
            String busqueda = request.getParameter("busqueda");
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada vistaAtributoFachada = new VistaAtributoFachada();
            List<VistaAtributo> vistaAtributos = vistaAtributoFachada.getListByPagination(condicion);
            for (VistaAtributo va : vistaAtributos) {
                JSONObject obj = new JSONObject();
                obj.put("codVA", va.getCodigo());
                obj.put("valor", va.getValor());
                obj.put("codVista", va.getVistaCodigo().getCodigo());
                obj.put("nombre", va.getVistaCodigo().getNombre());
                obj.put("codAtr", va.getCodigo());
                obj.put("referencia", va.getAtributoCodigo().getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);
        }
    }

    public void elimiarVistaAtributo(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada vistaAtributoFachada = new VistaAtributoFachada();
        int codVistaAtributo = Integer.parseInt(request.getParameter("codigo"));
        VistaAtributo vistaAtributo = new VistaAtributo();
        vistaAtributo.setCodigo(codVistaAtributo);
        vistaAtributoFachada.deleteObject(vistaAtributo);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Elimino Correctamente la Asociación", "success"));
    }

    public void getAtributoConPaginacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String rango = request.getParameter("rango");
            String condicionesPag = request.getParameter("condicionesPag");
            String busqueda = request.getParameter("busqueda");
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada atributoFachada = new AtributoFachada();
            List<Atributo> atributos = atributoFachada.getListByPagination(condicion);
            for (Atributo atributo : atributos) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", atributo.getCodigo());
                obj.put("referencia", atributo.getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);
        }
    }

    public void getAtributoVistaConCondicion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String condicion = request.getParameter("condicion");
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada vistaAtributoFachada = new VistaAtributoFachada();
            List<VistaAtributo> vistaAtributos = vistaAtributoFachada.getListByCondition(condicion);
            for (VistaAtributo va : vistaAtributos) {
                JSONObject obj = new JSONObject();
                obj.put("codVA", va.getCodigo());
                obj.put("valor", va.getValor());
                obj.put("codVista", va.getVistaCodigo().getCodigo());
                obj.put("nombre", va.getVistaCodigo().getNombre());
                obj.put("codAtr", va.getCodigo());
                obj.put("referencia", va.getAtributoCodigo().getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);
        }
    }

    public void getAtributosConCondicion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String condicion = request.getParameter("condicion");
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada atributoFachada = new AtributoFachada();
            List<Atributo> atributos = atributoFachada.getListByCondition(condicion);
            for (Atributo atributo : atributos) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", atributo.getCodigo());
                obj.put("referencia", atributo.getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);

        }
    }

    public void getAtributos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada atributoFachada = new AtributoFachada();
            List<Atributo> atributos = atributoFachada.getListObject();
            for (Atributo atributo : atributos) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", atributo.getCodigo());
                obj.put("referencia", atributo.getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);

        }
    }

    public void asociarAtributoVista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada vistaAtributoFachada = new VistaAtributoFachada();
        try (PrintWriter out = response.getWriter()) {
            int codAtributo = Integer.parseInt(request.getParameter("codAtributo"));
            int codVista = Integer.parseInt(request.getParameter("codVista"));
            String valor = request.getParameter("valor");
            VistaAtributo vistaAtributo = new VistaAtributo();
            Vista vista = new Vista();
            vista.setCodigo(codVista);
            Atributo atributo = new Atributo();
            atributo.setCodigo(codAtributo);
            vistaAtributo.setVistaCodigo(vista);
            vistaAtributo.setAtributoCodigo(atributo);
            vistaAtributo.setValor(valor);
            if (vistaAtributoFachada.insertObject(vistaAtributo) > 0) {
                request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Asocio el Atributo", "success"));
            } else {
                request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "No se Asocio el Atributo", "danger"));
            }
        }
    }

    public void insertarAtributo(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada atributoFachada = new AtributoFachada();
        Atributo atributo = new Atributo();
        atributo.setReferencia(request.getParameter("referencia"));
        int valida = atributoFachada.insertObject(atributo);
        if (valida > 0) {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Creo el Atributo", "success"));
        } else {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "Fallo Creacion del Atributo", "danger"));
        }
    }

    public void eliminarAtributo(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada atributoFachada = new AtributoFachada();
        Atributo atributo = new Atributo();
        atributo.setCodigo(Integer.parseInt(request.getParameter("codigo")));
        atributoFachada.deleteObject(atributo);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Elimino el Atributo", "success"));
    }

    public void getVistas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada vistaFachada = new VistaFachada();
        JSONArray arrayVista = new JSONArray();
        try (PrintWriter out = response.getWriter()) {
            List<Vista> vistas = vistaFachada.getListObject();
            for (Vista v : vistas) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", v.getCodigo());
                obj.put("nombre", v.getNombre());
                arrayVista.add(obj);
            }
            out.print(arrayVista);
        }
    }

    public void getVistaAtributo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayAtributos = new JSONArray();
            GestionFachada vistaAtributoFachada = new VistaAtributoFachada();
            List<VistaAtributo> vistaAtributos = vistaAtributoFachada.getListObject();
            for (VistaAtributo va : vistaAtributos) {
                JSONObject obj = new JSONObject();
                obj.put("codVA", va.getCodigo());
                obj.put("valor", va.getValor());
                obj.put("codVista", va.getVistaCodigo().getCodigo());
                obj.put("nombre", va.getVistaCodigo().getNombre());
                obj.put("codAtr", va.getCodigo());
                obj.put("referencia", va.getAtributoCodigo().getReferencia());
                arrayAtributos.add(obj);
            }
            out.print(arrayAtributos);
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
