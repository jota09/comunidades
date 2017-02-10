/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.MenuFachada;
import fachada.VistaFachada;
import fachada.VistaGViewFachada;
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
import persistencia.entidades.Menu;
import persistencia.entidades.Vista;
import persistencia.entidades.VistaGView;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(urlPatterns = {"/GestionMenuControlador"})
public class GestionMenuControlador extends HttpServlet {

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
                getMenus(request, response);
                break;
            }
            case 2: {
                getVistasGview(request, response);
                break;
            }
            case 3: {
                agregarMenu(request, response);
                break;
            }
            case 4: {
                eliminarMenu(request, response);
                break;
            }
        }
    }

    private void eliminarMenu(HttpServletRequest request, HttpServletResponse response) {
        GestionFachada menuFachada = new MenuFachada();
        Menu menu = new Menu();
        menu.setCodigo(Integer.parseInt(request.getParameter("codMenu")));
        menuFachada.deleteObject(menu);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "<strong>Se Agrego la Nueva Opción</strong>", "success"));
    }

    private void agregarMenu(HttpServletRequest request, HttpServletResponse response) {
        int codPadre = Integer.parseInt(request.getParameter("codPadre"));
        int codVista = Integer.parseInt(request.getParameter("codVista"));
        String nombreMenu = request.getParameter("nombreMenu");
        String urlMenu = request.getParameter("urlMenu");
        String nombreVista = request.getParameter("nombreVista");
        String onVista = request.getParameter("onVista");
        boolean inserta = true;
        if (!nombreMenu.isEmpty()) {
            GestionFachada menuFachada = new MenuFachada();
            Menu menu = new Menu();

            menu.setCodigoPadre(codPadre);

            menu.setNombre(nombreMenu);
            menu.setUrl(urlMenu);

            if (onVista.equals("1")) {
                if (codVista != 0 && !urlMenu.isEmpty()) {
                    GestionFachada vistaFachada = new VistaFachada();
                    Vista vista = new Vista();
                    vista.setCodigo(codVista);
                    vista.setNombre(nombreVista);
                    vista.setUrl(urlMenu);
                    vistaFachada.insertObject(vista);
                } else {
                    inserta = false;
                }
            }
            if (inserta) {
                menuFachada.insertObject(menu);
                request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "<strong>Se Agrego la Nueva Opción</strong>", "success"));
            } else {
                request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "<strong>Datos Invalidos o Faltantes</strong>", "danger"));
            }
        } else {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "<strong>Datos Invalidos o Faltantes</strong>", "danger"));
        }
    }

    private void getVistasGview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada vistasFachada = new VistaGViewFachada();
        List<VistaGView> vistas = vistasFachada.getListObject();
        JSONArray array = new JSONArray();
        if (vistas.size() > 0) {
            for (VistaGView vg : vistas) {
                JSONObject v = new JSONObject();
                v.put("codigo", vg.getCodigo());
                v.put("nombre", vg.getNombre());
                array.add(v);
            }
        } else {
            JSONObject v = new JSONObject();
            v.put("codigo", "0");
            v.put("nombre", "No Hay Vistas Nuevas");
            array.add(v);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(array);
        }
    }

    private void getMenus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada menuFachada = new MenuFachada();
        List<Menu> menus = menuFachada.getListObject();
        String html = construyeMenu(menus);
        try (PrintWriter out = response.getWriter()) {
            out.print(html);
        }
    }

    private String construyeMenu(List<Menu> menus) {
        String html = "";
        for (Menu m : menus) {
            if (m.getListaMenu().size() > 0) {
                html += "<div class=\"panel-group\">"
                        + "  <div class=\"panel panel-primary\">"
                        + "    <div class=\"panel-heading\">"
                        + "      <h4 class=\"panel-title\">"
                        + "        <a data-toggle=\"collapse\" href=\"#collapse" + m.getCodigo() + "\"><strong>" + m.getNombre() + "</a>"
                        + "</strong><div class='pull-right'><span class='badge sp ' onclick=\"agregarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-plus '></span></span>"
                        + "<span class='badge sp' onclick=\"editarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-pencil '></span></span>"
                        + "<span class='badge sp' onclick=\"eliminarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-trash '></span></span>"
                        + "</div> "
                        + "      </h4>"
                        + "    </div>"
                        + "    <div id=\"collapse" + m.getCodigo() + "\" class=\"panel-collapse collapse\">"
                        + "      <div class=\"panel-body per" + m.getCodigo() + "\">"
                        + "<div class=\"list-group\">";
                html += construyeMenu(m.getListaMenu());
                html += "</div></div>"
                        + "    </div>"
                        + "  </div>"
                        + "</div>";
            } else {
                html += "<div  class=\"list-group-item\"><strong>" + m.getNombre() + "</strong><div class='pull-right'><span class='badge sp success' onclick=\"agregarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-plus  '></span></span>"
                        + "<span class='badge sp' onclick=\"editarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-pencil info '></span></span>"
                        + "<span class='badge sp' onclick=\"eliminarMenu(" + m.getCodigo() + ")\" ><span class='glyphicon glyphicon-trash  danger'></span></span>"
                        + "</div></div><br/>";
            }
        }
        return html;
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
