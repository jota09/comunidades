package controladores;

import fachada.AtributoFachada;
import fachada.MenuFachada;
import fachada.RecursoFachada;
import fachada.VistaFachada;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.entidades.Atributo;
import persistencia.entidades.Menu;
import persistencia.entidades.Perfil;
import persistencia.entidades.Recurso;
import persistencia.entidades.Usuario;
import persistencia.entidades.Vista;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

@WebServlet( urlPatterns = {"/"})
public class GeneradorView extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String view = (String) session.getAttribute("view");//getAttribute("view");
        if(view==null){
            view=request.getParameter("view");
        }
        session.removeAttribute("view");
        try (PrintWriter out = response.getWriter()) {
            String rutaView = LecturaConfig.getValue("pathView");
            if (view == null) {
                view = LecturaConfig.getValue("paginicio");
            }
            VistaFachada vistaFachada = new VistaFachada();
            Vista vista = new Vista();
            vista.setUrl(view);
            vista = (Vista) vistaFachada.getObject(vista);
            rutaView += vista.getCodigo()+".view";
            File file = new File(rutaView);
            FileReader fR = new FileReader(file);
            BufferedReader bR = new BufferedReader(fR);
            String pagina = "";
            while (bR.ready()) {
                pagina += bR.readLine();
            }
            AtributoFachada atributoFachada = new AtributoFachada();
            List<Atributo> atributos = atributoFachada.getListObject(vista);
            for (Atributo atributo : atributos) {
                pagina = pagina.replace(atributo.getReferencia(), atributo.getValor());
            }
            RecursoFachada recursoFachada = new RecursoFachada();
            List<Recurso> recursos = recursoFachada.getListObject(vista);
            for (Recurso recurso : recursos) {
                recurso.setRuta(recurso.getRuta().replace("{ipservidor}", LecturaConfig.getValue("ipservidor")));
                pagina = pagina.replace(recurso.getNombre(), recurso.getRuta());
            }
            bR.close();
            fR.close();
            Object errores = session.getAttribute("message");
            if (errores != null) {
                pagina = pagina.replace("<@message@>", String.valueOf(errores));
                session.removeAttribute("message");
            } else {
                pagina = pagina.replace("<@message@>", "");
            }
            if (view.equals("menuprincipal.html") && session.getAttribute("user") != null) {
                Usuario user=(Usuario)session.getAttribute("user");
                Perfil pf = new Perfil();
                pf.setCodigo(user.getPerfilCodigo());
                MenuFachada menFac = new MenuFachada();
                List<Menu> menus = menFac.getListObject(pf);
                pagina=pagina.replace("<@menus@>", Utilitaria.construirMenu(menus));
            }
            out.print(pagina);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
