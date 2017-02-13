/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.EstructuraFachada;
import fachada.GestionFachada;
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
import persistencia.entidades.Estructura;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(urlPatterns = {"/EstructuraControlador"})
public class EstructuraControlador extends HttpServlet {

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
                getListEstructura(request, response);
                break;
            }
            case 2: {
                insertarParametro(request, response);
                break;
            }
            case 3: {
                getParametro(request, response);
                break;
            }
            case 4: {
                eliminarParametro(request, response);
                break;
            }
        }

    }

    private void eliminarParametro(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada estructuraFachada = new EstructuraFachada();
        Estructura estructura = new Estructura();
        estructura.setCodigo(Integer.parseInt(request.getParameter("codigo")));
        estructuraFachada.deleteObject(estructura);
    }

    private void getParametro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada estructuraFachada = new EstructuraFachada();
        Estructura estructura = new Estructura();
        estructura.setCodigo(Integer.parseInt(request.getParameter("codigo")));
        estructura=(Estructura)estructuraFachada.getObject(estructura);
        JSONObject obj = new JSONObject();
        obj.put("referencia", estructura.getReferencia());
        obj.put("valor", estructura.getValor());
        obj.put("descripcion", estructura.getDescripcion());
        try (PrintWriter out = response.getWriter()) {
            out.print(obj);
        }
    }

    private void insertarParametro(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada estructuraFachada = new EstructuraFachada();
        Estructura estructura = new Estructura();
        String codigo = request.getParameter("codigo");
        estructura.setReferencia(request.getParameter("referencia"));
        estructura.setValor(request.getParameter("valor"));
        estructura.setDescripcion(request.getParameter("descripcion"));
        if (codigo.equals("")) {
            estructuraFachada.insertObject(estructura);
        } else {
            estructura.setCodigo(Integer.parseInt(codigo));
            estructuraFachada.updateObject(estructura);
        }
    }

    private void getListEstructura(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada estructuraFachada = new EstructuraFachada();
        List<Estructura> estructuras = estructuraFachada.getListObject();
        JSONArray array = new JSONArray();
        for (Estructura e : estructuras) {
            JSONObject object = new JSONObject();
            object.put("codigo", e.getCodigo());
            object.put("referencia", e.getReferencia());
            object.put("valor", e.getValor());
            object.put("descripcion", e.getDescripcion());
            array.add(object);
        }
        try (PrintWriter out = response.getWriter()) {
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
