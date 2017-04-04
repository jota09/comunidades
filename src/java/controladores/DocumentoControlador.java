/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.TipoDocumentoDocFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import persistencia.entidades.TipoDocumentoDoc;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.Utilitaria;

/**
 *
 * @author Jesus.Ramos
 */
@WebServlet(name = "DocumentoControlador", urlPatterns = {"/DocumentoControlador"})
public class DocumentoControlador extends HttpServlet {

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
                        recuperaTipoDocumento(request, response);
                        break;
                }
            }
            /* TODO output your page here. You may use following sample code. */

        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }
    }
    
    public void recuperaTipoDocumento(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()){
            GestionFachada tddFach = new TipoDocumentoDocFachada();
            TipoDocumentoDoc tdd = new TipoDocumentoDoc();
            tdd.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<TipoDocumentoDoc> listaTipo = tddFach.getListObject(tdd);
            JSONArray array = new JSONArray();
            for( TipoDocumentoDoc tdd2 : listaTipo ){
                JSONObject obj = new JSONObject();
                obj.put("id",tdd2.getId());
                obj.put("nombre",tdd2.getNombre());
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
