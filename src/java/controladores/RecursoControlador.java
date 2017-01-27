/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.RecursoFachada;
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
import persistencia.entidades.Recurso;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "RecursoControlador", urlPatterns = {"/RecursoControlador"})
public class RecursoControlador extends HttpServlet {

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
        int op = Integer.parseInt(request.getParameter("op"));

        switch (op) {
            case 1: {
                cargarTabla(request, response);
                break;
            }
            case 2: {
                actualizaOInserta(request, response);
                break;
            }
            case 3: {
                consultarRecurso(request, response);
                break;
            }
            case 4: {
                eliminarRecurso(request, response);
                break;
            }

        }
    }

    public void eliminarRecurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        try (PrintWriter out = response.getWriter()) {
            Recurso recurso = new Recurso();
            recurso.setCodigo(Integer.parseInt(request.getParameter("cod")));
            recursoFacade.deleteObject(recurso);
        }
    }

    public void consultarRecurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        try (PrintWriter out = response.getWriter()) {
            Recurso recurso = new Recurso();
            recurso.setCodigo(Integer.parseInt(request.getParameter("cod")));
            recursoFacade.getObject(recurso);
            JSONObject rec = new JSONObject();
            rec.put("codigo", recurso.getCodigo());
            rec.put("nombre", recurso.getNombre());
            rec.put("ruta", recurso.getRuta());
            out.print(rec);
        }
    }

    public void cargarTabla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayRecursos = new JSONArray();
            List<Recurso> recursos = (List<Recurso>) recursoFacade.getListObject();
            for (Recurso r : recursos) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", r.getCodigo());
                obj.put("nombre", r.getNombre());
                obj.put("ruta", r.getRuta().replace("<", "&lt;").replace(">", "&gt;"));
                arrayRecursos.add(obj);
            }
            out.print(arrayRecursos);

        }

    }

    public void actualizaOInserta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        try (PrintWriter out = response.getWriter()) {
            String codRecurso = request.getParameter("cod");
            String nombreRecurso = request.getParameter("nombreRecurso");
            String rutaRecurso = request.getParameter("rutaRecurso");
            Recurso recurso = new Recurso();
            recurso.setNombre(nombreRecurso);
            recurso.setRuta(rutaRecurso);
            recurso.setActivo((short) 1);
            if (codRecurso.equals("")) {
                recursoFacade.insertObject(recurso);
            } else {
                recurso.setCodigo(Integer.parseInt(codRecurso));
                recursoFacade.updateObject(recurso);
            }
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
