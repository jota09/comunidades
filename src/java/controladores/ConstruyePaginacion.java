package controladores;

import fachada.GestionFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(urlPatterns = {"/Pagina"})
public class ConstruyePaginacion extends HttpServlet {

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
        String obj = request.getParameter("obj");
        String tipo = request.getParameter("tipo");
        int codigoTipo = 0;
        try (PrintWriter out = response.getWriter()) {
            System.out.println(obj);
            Class clase = Class.forName("fachada." + obj + "Fachada");
            GestionFachada gestionFachada = (GestionFachada) clase.newInstance();
            if (tipo != null) {
                codigoTipo = Integer.parseInt(tipo);
            }
            int cont = 1;
            List<String> paginas = Utilitaria.getPaginacion(gestionFachada.getCount(codigoTipo), 10);
            out.println("<nav aria-label='Page navigation'>");
            out.println("<ul class='pagination'>");
            String li = "<li id='pag-" + cont + "' style=\"cursor:pointer;\" >";
            out.println(li);
            String a = "<a onclick='mostrarNoticia('" + paginas.get(0) + "', 'pag-" + cont + "',"+tipo+")'>";
            out.println(a);
            out.println("<span aria-hidden='true'>&laquo;</span>");
            out.println("</a>");
            out.println("</li>");
            for (String p : paginas) {
                li = "<li id='" + cont + "' style='cursor:pointer;'><a  onclick=\"mostrarNoticia('" + p + "', '" + cont + "',"+tipo+")\" >" + cont + "</a></li>";
                out.println(li);
                cont++;
            }
            li = "<li id='pag-" + cont + "' style='cursor:pointer;'>";
            a = "<a onclick=\"mostrarNoticia('" + paginas.get(paginas.size() - 1) + "', 'pag-" + cont + "',"+tipo+")\">";
            out.println(li);
            out.println(a);
            out.println("<span aria-hidden=\"true\">&raquo;</span>");
            out.println("</a>");
            out.println("</li>");
            out.println("</ul>");
            out.println("</nav>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConstruyePaginacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConstruyePaginacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConstruyePaginacion.class.getName()).log(Level.SEVERE, null, ex);
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