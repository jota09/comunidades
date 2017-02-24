/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.FiltroFachada;
import fachada.GestionFachada;
import fachada.OpcionesFiltroFachada;
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
import persistencia.entidades.Error;
import persistencia.entidades.Filtro;
import persistencia.entidades.OpcionesFiltro;
import persistencia.entidades.TipoError;
import persistencia.entidades.Vista;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "ConstruyeFiltro", urlPatterns = {"/ConstruyeFiltro"})
public class ConstruyeFiltro extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            String vi = request.getParameter("vista");
            System.out.println(vi);
            GestionFachada filtroFachada = new FiltroFachada();
            GestionFachada opcionesFachada = new OpcionesFiltroFachada();
            Vista vista = new Vista(Integer.parseInt(vi));
            JSONArray arrayFiltros = new JSONArray();
            List<Filtro> filtros = filtroFachada.getListByCondition(vista);
            for (Filtro f : filtros) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", f.getCodigo());
                obj.put("nombre", f.getNombre());
                obj.put("condicion", f.getCondicionFiltro().getCodigo());
                JSONArray arrayOpciones = new JSONArray();
                List<OpcionesFiltro> opciones = opcionesFachada.getListObject(f);
                for (OpcionesFiltro o : opciones) {
                    JSONObject op = new JSONObject();
                    op.put("codigo", o.getCodigo());
                    op.put("nombre", o.getNombre());
                    arrayOpciones.add(op);
                }
                obj.put("opciones", arrayOpciones);
                arrayFiltros.add(obj);
            }
            PrintWriter out = response.getWriter();
            out.print(arrayFiltros);
        } catch (IOException ex) {
            Error error = new Error();
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

}
