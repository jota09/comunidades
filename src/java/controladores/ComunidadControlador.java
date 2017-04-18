/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import utilitarias.CondicionPaginado;

/**
 *
 * @author daniel.franco
 */
@WebServlet(name = "ComunidadControlador", urlPatterns = {"/ComunidadControlador"})
public class ComunidadControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {
                case 1:
                    this.getRecursoVistaConPaginacion(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getRecursoVistaConPaginacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rango = request.getParameter("rango");
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda = request.getParameter("busqueda");
        GestionFachada condicionFachada = new CondicionPaginacionFachada();
        CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
        condicionFachada.getObject(condicionPaginacion);
        CondicionPaginado condicion = new CondicionPaginado();
        condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
        GestionFachada comunidadFachada = new ComunidadFachada();
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayComunidades = new JSONArray();
            List<Comunidad> comunidades = comunidadFachada.getListByPagination(condicion);
            for (Comunidad comunidad : comunidades) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", comunidad.getCodigo());
                obj.put("nit", comunidad.getNit());
                obj.put("nombre", comunidad.getNombre());
                obj.put("direccion", comunidad.getDireccion());
                obj.put("telefono", comunidad.getTelefono());
                obj.put("codigoBarras", comunidad.getIdBarCode());
                obj.put("miembros", comunidad.getMiembros());
                arrayComunidades.add(obj);
            }
            out.print(arrayComunidades);
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
