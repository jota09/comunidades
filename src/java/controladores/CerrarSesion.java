package controladores;

import fachada.SeguridadUsuarioFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.entidades.Error;
import persistencia.entidades.SeguridadUsuario;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.Utilitaria;

@WebServlet(name = "CerrarSesion", urlPatterns = {"/CerrarSesion"})
public class CerrarSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {
                case 1: {
                    cierraSesion(request, response);
                    break;
                }
                case 2: {
                    validaSesion(request, response);
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

    private void cierraSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();//el constructor con false permite que no vuelva a crear el obj session
        Usuario user = (Usuario) sesion.getAttribute("user");
        SeguridadUsuario sgUsr = user.getListaSeguridad();
        sgUsr.setIpUltimaSesion(request.getRemoteUser());
        SeguridadUsuarioFachada sgdadFach = new SeguridadUsuarioFachada();
        sgdadFach.updateObject(sgUsr);
        if (sesion.getAttribute("user") != null) {
            sesion.removeAttribute("user");
        }
        sesion.invalidate();
        //request.getServletContext().getRequestDispatcher("menuprincipal.jsp").forward(request, response);//redirige con los valores de request y response y mantiene la url
        response.sendRedirect("/Comunidades");//redirige totalmente a la pagina
    }

    private void validaSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession(false);
        try {
            Date tiempoValida = new Date(((Date) sesion.getAttribute("ultimaActividad")).getTime());
            Date tiempoActual = new Date();
            long tiempoSesion = tiempoValida.getTime() + (Long.parseLong((String) sesion.getAttribute("tiempoSesion")) * 1000);
            tiempoValida.setTime(tiempoSesion);
            if (tiempoActual.before(tiempoValida)) {
                out.print(1);
            } else {
                out.print(0);
            }
        } catch (NullPointerException ex) {
            out.print(0);
        }
    }

}
