package controladores;

import fachada.CondicionPaginacionFachada;
import fachada.GestionFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;

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
            throws ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String obj = request.getParameter("obj");
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda=request.getParameter("busqueda");
        int rango = Integer.parseInt(request.getParameter("rango"));
        try (PrintWriter out = response.getWriter()) {
            Class clase = Class.forName("fachada." + obj + "Fachada");
            GestionFachada gestionFachada = (GestionFachada) clase.newInstance();
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            if (condicionPaginacion.getActivaUsuario() == 1) {
                Usuario user = (Usuario) request.getSession().getAttribute("user");
                condicion.setUser(user);
            }
            if (condicionPaginacion.getActivaComunidad() == 1) {
                Usuario user = (Usuario) request.getSession().getAttribute("user");
                condicion.setComunidad(user.getPerfilCodigo().getComunidad());
            }
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda));
            List<String> paginas = Utilitaria.getPaginacion(gestionFachada.getCount(condicion), rango);
            int cont = 1;
            if (paginas.size() > 0) {
                out.println("<nav aria-label='Page navigation'>");
                out.println("<ul class='pagination'>");
                String li = "<li  id='pag-" + cont + "' style=\"cursor:pointer;\" >";
                out.println(li);
                String a = "<a onclick=\"mostrar" + obj + "('" + paginas.get(0) + "', 'pag-" + cont + "')\">";
                out.println(a);
                out.println("<span aria-hidden='true'>&laquo;</span>");
                out.println("</a>");
                out.println("</li>");
                for (String p : paginas) {
                    li = "<li " + ((cont == 1) ? "class='active'" : "") + " id='" + cont + "' style='cursor:pointer;'><a  onclick=\"mostrar" + obj + "('" + p + "', '" + cont + "')\" >" + cont + "</a></li>";
                    out.println(li);
                    cont++;
                }
                li = "<li id='pag-" + cont + "' style='cursor:pointer;'>";
                a = "<a onclick=\"mostrar" + obj + "('" + paginas.get(paginas.size() - 1) + "', 'pag-" + cont + "')\">";
                out.println(li);
                out.println(a);
                out.println("<span aria-hidden=\"true\">&raquo;</span>");
                out.println("</a>");
                out.println("</li>");
                out.println("</ul>");
                out.println("</nav>");
            }
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (InstantiationException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(5));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IllegalAccessException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(6));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
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
