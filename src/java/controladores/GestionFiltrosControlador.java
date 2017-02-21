package controladores;

import fachada.CondicionesFiltroFachada;
import fachada.FiltroFachada;
import fachada.GestionFachada;
import fachada.MetaDataFachada;
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
import persistencia.entidades.CondicionesFiltro;
import persistencia.entidades.Filtro;
import persistencia.entidades.MetaData;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "GestionFiltrosControlador", urlPatterns = {"/GestionFiltrosControlador"})
public class GestionFiltrosControlador extends HttpServlet {

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
                getTables(request, response);
                break;
            }
            case 2: {
                getColumnas(request, response);
                break;
            }
            case 3: {
                getFiltrosXTabla(request, response);
                break;
            }
            case 4: {
                getCondicionesFiltro(request, response);
                break;
            }
            case 5: {
                getCondicionFiltro(request, response);
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

    private void getTables(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MetaDataFachada metaFachada = new MetaDataFachada();
        JSONArray array = new JSONArray();
        List<MetaData> tablas = metaFachada.getTables();
        for (MetaData t : tablas) {
            JSONObject obj = new JSONObject();
            obj.put("tabla", t.getTabla());
            array.add(obj);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(array);
        }
    }

    private void getFiltrosXTabla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String entidad = request.getParameter("entidad");
        MetaData tabla = new MetaData();
        tabla.setTabla(entidad);
        GestionFachada filtroFachada = new FiltroFachada();
        List<Filtro> filtros = filtroFachada.getListObject(tabla);
        JSONArray array = new JSONArray();
        for (Filtro f : filtros) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", f.getCodigo());
            obj.put("campo", f.getCampo());
            obj.put("nombre", f.getNombre());
            obj.put("condicion", f.getCondicion());
            array.add(obj);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(array);
        }
    }

    private void getColumnas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MetaDataFachada metaFachada = new MetaDataFachada();
        String entidad = request.getParameter("entidad");
        MetaData tabla = new MetaData();
        tabla.setTabla(entidad);
        JSONArray array = new JSONArray();
        List<String> columnas = metaFachada.getColumnas(tabla);
        for (String col : columnas) {
            JSONObject obj = new JSONObject();
            obj.put("columna", col);
            array.add(obj);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(array);
        }
    }

    private void getCondicionesFiltro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada condicionesFachada = new CondicionesFiltroFachada();
        List<CondicionesFiltro> condiciones = condicionesFachada.getListObject();
        JSONArray array = new JSONArray();
        for (CondicionesFiltro con : condiciones) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", con.getCodigo());
            obj.put("condicion", con.getCondicion());
            array.add(obj);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(array);
        }
    }

    private void getCondicionFiltro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        GestionFachada condicionFachada = new CondicionesFiltroFachada();
        CondicionesFiltro condiciones = new CondicionesFiltro(codigo);
        condicionFachada.getObject(condiciones);
        try (PrintWriter out = response.getWriter()) {
            out.print(condiciones.getParametros());
        }
    }

}
