/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
import fachada.GestionFachada;
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
import persistencia.entidades.Perfil;
import persistencia.entidades.Usuario;
import utilitarias.LecturaConfig;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "ValidarComunidadControlador", urlPatterns = {"/ValidarComunidadControlador"})
public class ValidarComunidadControlador extends HttpServlet {

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
            int op = Integer.parseInt(request.getParameter("op"));
            switch (op) {
                case 1: {
                    getPerfiles(request, response);
                    break;
                }
                case 2: {
                    redirigirAComunidad(request, response);
                    break;
                }
                case 3: {
                    redirigirAValidaComunidad(request, response);
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

    private void redirigirAComunidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int posicion=Integer.parseInt(request.getParameter("posicion"));
        List<Perfil> perfiles = (List<Perfil>) request.getSession().getAttribute("perfiles");
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("user");
        Perfil perfil = perfiles.get(posicion);
        user.setPerfilCodigo(perfil);
        session.setAttribute("user", user);
        request.getSession().setAttribute("view", "menuprincipal.html");
        response.sendRedirect("/Comunidades");
    }

    private void getPerfiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Perfil> perfiles = (List<Perfil>) request.getSession().getAttribute("perfiles");
        JSONArray array = new JSONArray();
        int posicion=0;
        for (Perfil p : perfiles) {
            Comunidad comunidad = p.getComunidad();
            JSONObject com = new JSONObject();
            com.put("codigo", comunidad.getCodigo());
            com.put("nombre", comunidad.getNombre());
            com.put("rutaImg", LecturaConfig.getValue("rutaImg") + "logo/" + comunidad.getCodigo() + ".png");
            com.put("codigoPerfil", p.getCodigo());
            com.put("pos",posicion);
            array.add(com);
            posicion++;
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

    private void redirigirAValidaComunidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("perfiles") != null) {
            session.setAttribute("view", "validacomunidad.html");
        }
        response.sendRedirect("/Comunidades");
    }

}
