/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.MenuFachada;
import fachada.PerfilFachada;
import fachada.PerfilMenuFachada;
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
import persistencia.entidades.Perfil;
import persistencia.entidades.PerfilMenu;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(urlPatterns = "/GestionPermisosControlador")
public class GestionPermisosControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        switch (op) {
            case 1: {
                getPerfiles(request, response);
                break;
            }
            case 2: {
                getPermisos(request, response);
                break;
            }
            case 3: {
                validaPermisos(request, response);
                break;
            }
            case 4: {
                cambiarPermisos(request, response);
                break;
            }
        }
    }

    private void cambiarPermisos(HttpServletRequest request, HttpServletResponse response) {
        String permis = request.getParameter("permisos");
        Perfil perfil = new Perfil();
        perfil.setCodigo(Integer.parseInt(request.getParameter("perfil")));
        GestionFachada perfilMenuFachada = new PerfilMenuFachada();
        perfilMenuFachada.deleteObject(perfil);
        if (!permis.equals("")) {
            String[] permisos = permis.replace("permiso", "").split(";");
            for (String p : permisos) {
                if (!p.equals("")) {
                    PerfilMenu perfilMenu = new PerfilMenu();
                    perfilMenu.setMenuCodigo(Integer.parseInt(p));
                    perfilMenu.setPerfilCodigo(perfil.getCodigo());
                    perfilMenuFachada.insertObject(perfilMenu);

                }
            }
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "<strong>Se Modifico el Perfil</strong>", "success"));
    }

    private void validaPermisos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Perfil perfil = new Perfil();
        JSONArray arrayMenus = new JSONArray();
        perfil.setCodigo(Integer.parseInt(request.getParameter("perfil")));
        GestionFachada perfilMenuFachada = new PerfilMenuFachada();
        List<Menu> menus = perfilMenuFachada.getListObject(perfil);
        for (Menu m : menus) {
            JSONObject menu = new JSONObject();
            menu.put("codigo", m.getCodigo());
            arrayMenus.add(menu);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(arrayMenus);
        }

    }

    private void getPerfiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray arrayPerfil = new JSONArray();
        GestionFachada perfilFachada = new PerfilFachada();
        List<Perfil> perfiles = perfilFachada.getListObject();
        for (Perfil p : perfiles) {
            JSONObject perfil = new JSONObject();
            perfil.put("codigo", p.getCodigo());
            perfil.put("nombre", p.getNombre());
            perfil.put("nombreComunidad",p.getComunidad().getNombre());
            arrayPerfil.add(perfil);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(arrayPerfil);
        }
    }

    private void getPermisos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada menuFachada = new MenuFachada();
        List<Menu> menus = menuFachada.getListObject();
        String html = construyePermisos(menus);
        try (PrintWriter out = response.getWriter()) {
            out.print(html);
        }
    }

    private String construyePermisos(List<Menu> menus) {
        String html = "";
        for (Menu m : menus) {
            if (m.getListaMenu().size() > 0) {
                html += "<div class=\"panel-group\">"
                        + "  <div class=\"panel panel-info\">"
                        + "    <div class=\"panel-heading\">"
                        + "      <h4 class=\"panel-title\">"
                        + "        <a data-toggle=\"collapse\" href=\"#collapse" + m.getCodigo() + "\"><strong>" + m.getNombre() + "</strong></a><input id='permiso" + m.getCodigo() + "' type='checkbox' onclick=\"desChequeaCheck(" + m.getCodigo() + ")\" class='  permisos pull-right'>"
                        + "      </h4>"
                        + "    </div>"
                        + "    <div id=\"collapse" + m.getCodigo() + "\" class=\"panel-collapse collapse\">"
                        + "      <div class=\"panel-body per" + m.getCodigo() + "\">"
                        + "<div class=\"list-group\">";
                html += construyePermisos(m.getListaMenu());
                html += "</div></div>"
                        + "    </div>"
                        + "  </div>"
                        + "</div>";
            } else {
                html += "<div  class=\"list-group-item\"><strong>" + m.getNombre() + "</strong><input id='permiso" + m.getCodigo() + "' type='checkbox' onclick=\"chequeaPadre(" + m.getCodigoPadre() + ")\" class='  permisos pull-right'></div><br/>";
            }
        }
        return html;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
