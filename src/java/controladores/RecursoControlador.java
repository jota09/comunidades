/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.RecursoFachada;
import fachada.RecursoVistaFachada;
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
import persistencia.entidades.Recurso;
import persistencia.entidades.RecursoVista;
import persistencia.entidades.Vista;
import utilitarias.Utilitaria;

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
                getRecursos(request, response);
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
            case 5: {
                getRecursosVista(request, response);
                break;
            }
            case 6: {
                getVistas(request, response);
                break;
            }
            case 7: {
                eliminarAso(request, response);
                break;
            }
            case 8: {
                asociarRecursoAVista(request, response);
                break;
            }

        }
    }

    public void asociarRecursoAVista(HttpServletRequest request, HttpServletResponse response) {
        int codRecurso = Integer.parseInt(request.getParameter("codRecurso"));
        int codVista = Integer.parseInt(request.getParameter("codVista"));
        Recurso recurso = new Recurso();
        recurso.setCodigo(codRecurso);
        Vista vista = new Vista();
        vista.setCodigo(codVista);
        RecursoVista recursoVista = new RecursoVista();
        recursoVista.setRecursoCodigo(recurso);
        recursoVista.setVistaCodigo(vista);
        GestionFachada recursoVistaFachada = new RecursoVistaFachada();
        if (recursoVistaFachada.insertObject(recursoVista) > 0) {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Asocio el Recurso", "success"));
        } else {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "No se Asocio el Recurso", "danger"));
        }

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

    public void eliminarRecurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        Recurso recurso = new Recurso();
        recurso.setCodigo(Integer.parseInt(request.getParameter("cod")));
        recursoFacade.deleteObject(recurso);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Elimino el Recurso", "success"));

    }

    public void eliminarAso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoVistaFacade = new RecursoVistaFachada();
        RecursoVista recursoVista = new RecursoVista();
        recursoVista.setCodigo(Integer.parseInt(request.getParameter("cod")));
        recursoVistaFacade.deleteObject(recursoVista);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Elimino la Asociacion", "success"));

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

    public void getRecursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoFacade = new RecursoFachada();
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayRecursos = new JSONArray();
            List<Recurso> recursos = recursoFacade.getListObject();
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

    public void getRecursosVista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada recursoVistaFachada = new RecursoVistaFachada();
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayRecursos = new JSONArray();
            List<RecursoVista> recursoVistas = (List<RecursoVista>) recursoVistaFachada.getListObject();
            for (RecursoVista rv : recursoVistas) {
                JSONObject obj = new JSONObject();
                obj.put("codRecurso", rv.getRecursoCodigo().getCodigo());
                obj.put("nombreRecurso", rv.getRecursoCodigo().getNombre());
                obj.put("codVista", rv.getVistaCodigo().getCodigo());
                obj.put("nombreVista", rv.getVistaCodigo().getNombre());
                obj.put("codRV", rv.getCodigo());
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
                if (recursoFacade.insertObject(recurso) > 0) {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Agrego el Nuevo Recurso", "success"));
                } else {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "No Se Pudo Agregar el Nuevo Recurso", "danger"));
                }

            } else {
                recurso.setCodigo(Integer.parseInt(codRecurso));
                if (recursoFacade.updateObject(recurso) > 0) {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se Actualizo el Recurso", "success"));
                } else {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "No Se Actualizo el Recurso", "success"));
                }
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
